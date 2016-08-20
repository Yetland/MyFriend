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
import com.yetland.myfriend.model.ResponseModel;
import com.yetland.myfriend.model.SchoolMateModel;
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

public class LeaveMessageActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.et_leave_message)
    EditText etLeaveMessage;
    @Bind(R.id.bt_leave_message)
    Button btLeaveMessage;
    @Bind(R.id.tv_to_id_nick)
    TextView tvToIdNick;

    @Inject
    RestApi restApi;
    private String contentMessage;
    private UserModel userModel;// 当前用户信息
    private SchoolMateModel schoolMateModel;// 留言人信息
    private CustomProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_message);
        ButterKnife.bind(this);
        toolbar.setTitle(" ");
        tvTitle.setText("留言");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        schoolMateModel = (SchoolMateModel) getIntent().getSerializableExtra("schoolMateModel");
        userModel = (UserModel) SharedPreferenceUtil.readUserModel(LeaveMessageActivity.this);
        tvToIdNick.setText(schoolMateModel.getNick());

    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerApiComponent.builder()
                .appComponent(appComponent)
                .apiModule(new ApiModule(this))
                .build().inject(this);
    }

    @OnClick(R.id.bt_leave_message)
    public void onClick() {

        String input = etLeaveMessage.getText().toString();
        input = input.replaceAll(" ", "");
        if (TextUtils.isEmpty(input)) {
            Toast.makeText(LeaveMessageActivity.this, "留言信息为空", Toast.LENGTH_SHORT).show();
        } else {
            dialog = CustomProgressDialog.show(LeaveMessageActivity.this, "正在留言...", false, null);
            contentMessage = etLeaveMessage.getText().toString();
            restApi.leaveMessage(userModel.getId(), schoolMateModel.getToId(), contentMessage)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .subscribe(new Subscriber<ResponseModel>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            dialog.dismiss();
                            ErrorBundle errorBundle = new DefaultErrorBundle((Exception) e);
                            String msg = ErrorMessageFactory.creat(LeaveMessageActivity.this, errorBundle.getException());
                            Toast.makeText(LeaveMessageActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNext(ResponseModel responseModel) {
                            dialog.dismiss();
                            if (responseModel.getCode().equals(ResultCodeList.LEAVE_MESSAGE_BOARD_SUCCESS)) {
                                Toast.makeText(LeaveMessageActivity.this, "留言成功", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(LeaveMessageActivity.this, responseModel.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
