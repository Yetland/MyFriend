package com.yetland.myfriend.view.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yetland.myfriend.R;
import com.yetland.myfriend.core.view.activity.BaseActivity;
import com.yetland.myfriend.core.data.ResultCodeList;
import com.yetland.myfriend.core.api.RestApi;
import com.yetland.myfriend.core.exception.DefaultErrorBundle;
import com.yetland.myfriend.core.exception.ErrorBundle;
import com.yetland.myfriend.core.exception.ErrorMessageFactory;
import com.yetland.myfriend.model.ActivityModel;
import com.yetland.myfriend.model.ResponseModel;
import com.yetland.myfriend.model.UserModel;
import com.yetland.myfriend.util.SharedPreferenceUtil;
import com.yetland.myfriend.view.widget.CustomProgressDialog;
import com.yetland.myfriend.core.dagger2.component.AppComponent;
import com.yetland.myfriend.core.dagger2.component.DaggerApiComponent;
import com.yetland.myfriend.core.module.ApiModule;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CreateActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.activity_name)
    EditText activityName;
    @Bind(R.id.activity_detail)
    EditText activityDetail;
    @Bind(R.id.ensure)
    Button ensure;

    @Inject
    RestApi restApi;

    private UserModel userModel;
    private ActivityModel activityModel;
    private String title, contentMessage;
    private CustomProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat);
        ButterKnife.bind(this);
        toolbar.setTitle(" ");
        tvTitle.setText("创建活动");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        userModel = (UserModel) SharedPreferenceUtil.readUserModel(CreateActivity.this);
        activityModel = new ActivityModel();
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerApiComponent.builder()
                .appComponent(appComponent)
                .apiModule(new ApiModule(this))
                .build()
                .inject(this);
    }

    @OnClick(R.id.ensure)
    public void onClick() {

        title = activityName.getText().toString().replaceAll(" ", "");
        contentMessage = activityDetail.getText().toString().replaceAll(" ", "");
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(contentMessage)) {

        } else {
            activityModel.setTitle(title);
            activityModel.setContentMessage(contentMessage);
            activityModel.setCreatorId(userModel.getId());

            dialog = CustomProgressDialog.show(CreateActivity.this, "创建活动中...", false, null);

            restApi.createActivity(activityModel)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .subscribe(new Subscriber<ResponseModel>() {

                        @Override
                        public void onCompleted() {
                            dialog.dismiss();
                        }

                        @Override
                        public void onError(Throwable e) {
                            dialog.dismiss();
                            ErrorBundle errorBundle = new DefaultErrorBundle((Exception) e);
                            String msg = ErrorMessageFactory.creat(CreateActivity.this, errorBundle.getException());
                            Toast.makeText(CreateActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNext(ResponseModel responseModel) {
                            dialog.dismiss();
                            if (responseModel.getCode().equals(ResultCodeList.CREATE_ACTIVITY_SUCCESS)){
                                Toast.makeText(CreateActivity.this, "创建成功", Toast.LENGTH_SHORT).show();

                                setResult(ResultCodeList.CREATE_SUCCESS);
                                CreateActivity.this.finish();
                            }else{
                                Toast.makeText(CreateActivity.this, responseModel.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
    }
}
