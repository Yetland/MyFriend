package com.yetland.myfriend.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import com.yetland.myfriend.view.adapter.SchoolMateAdapter;
import com.yetland.myfriend.core.dagger2.component.AppComponent;
import com.yetland.myfriend.core.dagger2.component.DaggerApiComponent;
import com.yetland.myfriend.core.module.ApiModule;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MySchoolMateActivity extends BaseActivity {

    private static final String TAG = "MySchoolMateActivity";
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.rv_my_school_mate)
    RecyclerView rvMySchoolMate;
    @Bind(R.id.load_view_bt_retry)
    Button loadViewBtRetry;
    @Bind(R.id.load_view_tv_error_message)
    TextView loadViewTvErrorMessage;
    @Bind(R.id.retry_view)
    LinearLayout retryView;
    @Bind(R.id.load_view_pg_loading)
    ProgressBar loadViewPgLoading;
    @Bind(R.id.load_view_tv_loading)
    TextView loadViewTvLoading;
    @Bind(R.id.load_view_tv_no_result)
    TextView loadViewTvNoResult;
    @Bind(R.id.loading_view)
    LinearLayout loadingView;

    @Inject
    RestApi restApi;
    List<SchoolMateModel> schoolMateModels;
    private UserModel userModel;
    private List<Integer> schoolMateIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_school_mate);
        ButterKnife.bind(this);

        userModel = new UserModel();
        userModel = (UserModel) getIntent().getSerializableExtra("user");
        if (userModel != null) {
            toolbar.setTitle(" ");
            tvTitle.setText("我的校友");

            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(view -> onBackPressed());
            schoolMateIds = new ArrayList<>();
            getMySchoolMates(userModel.getId());
        } else {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_my_school_mate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", userModel);
        switch (id) {
            case R.id.action_search:
                intent.putExtras(bundle);
                intent.putIntegerArrayListExtra("schoolMateIds", (ArrayList<Integer>) schoolMateIds);
                intent.setClass(MySchoolMateActivity.this, SearchSchoolMateActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerApiComponent.builder()
                .appComponent(appComponent)
                .apiModule(new ApiModule(this))
                .build()
                .inject(this);
    }

    public void showLoading() {
        loadingView.setVisibility(View.VISIBLE);
    }

    public void hideLoading() {
        loadingView.setVisibility(View.INVISIBLE);
    }

    public void showRetry() {
        retryView.setVisibility(View.VISIBLE);
    }

    public void hideRetry() {
        retryView.setVisibility(View.INVISIBLE);
    }

    public void showNoResult() {
        loadViewTvNoResult.setVisibility(View.VISIBLE);
        loadViewTvNoResult.setText("你还没有校友");
    }

    public void hideNoResult() {
        loadViewTvNoResult.setVisibility(View.INVISIBLE);
    }

    public void showResult() {
        rvMySchoolMate.setVisibility(View.VISIBLE);
    }

    public void hideResult() {
        rvMySchoolMate.setVisibility(View.INVISIBLE);
    }

    public void showErrorMessage(String errorMessage) {
        loadViewTvErrorMessage.setText(errorMessage);
    }

    public Context getContext() {
        return MySchoolMateActivity.this;
    }

    public void getMySchoolMates(int fromId) {
        loading();
        restApi.getMySchoolMate(fromId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ResponseModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        loadFailure();
                        ErrorBundle errorBundle = new DefaultErrorBundle((Exception) e);
                        String errorMessage = ErrorMessageFactory.creat(getContext(), errorBundle.getException());
                        showErrorMessage(errorMessage);
                    }

                    @Override
                    public void onNext(ResponseModel responseModel) {
                        if (responseModel == null && responseModel.getCode() == ResultCodeList.GET_SCHOOL_MATE_SUCCESS) {
                            loadFailure();
                            showErrorMessage("加载失败...");
                        } else {

                            schoolMateModels = responseModel.getContent().getSchoolMateModels();
                            if (schoolMateModels == null) {
                                loadFailure();
                            } else if (schoolMateModels.size() == 0) {
                                noSchoolMate();
                            } else {
                                SharedPreferenceUtil.saveSchoolMateList(MySchoolMateActivity.this, schoolMateModels);
                                renderData(schoolMateModels);
                                for (SchoolMateModel schoolMateModel : schoolMateModels) {
                                    int id = schoolMateModel.getToId();
                                    Log.e(TAG, "onNext:" + id);
                                    schoolMateIds.add(id);
                                }
                                loadSuccess();
                            }
                        }
                    }
                });
    }

    private void renderData(List<SchoolMateModel> schoolMateModels) {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayout.VERTICAL);

        rvMySchoolMate.setLayoutManager(manager);
        SchoolMateAdapter adapter = new SchoolMateAdapter(schoolMateModels, userModel);
        rvMySchoolMate.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener(position -> {
            Log.e(TAG, "renderData:" + position);
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("schoolMateModel", schoolMateModels.get(position));
            bundle.putSerializable("userModel", userModel);

            intent.putExtras(bundle);
            intent.setClass(MySchoolMateActivity.this, UserDetailActivity.class);

            startActivity(intent);
        });
    }

    private void noSchoolMate() {
        showNoResult();
        hideRetry();
        hideLoading();
        hideResult();
    }

    private void loadSuccess() {
        hideLoading();
        hideRetry();
        hideNoResult();
        showResult();
    }

    private void loadFailure() {
        showRetry();
        hideLoading();
        hideNoResult();
        hideResult();
    }

    private void loading() {
        showLoading();
        hideRetry();
        hideNoResult();
        hideResult();
    }

    @OnClick(R.id.load_view_bt_retry)
    public void onClick() {
        getMySchoolMates(userModel.getId());
    }

    @Override
    protected void onResume() {
        schoolMateModels = SharedPreferenceUtil.readSchoolMateList(this);
        if (schoolMateModels.size() == 0) {
            noSchoolMate();
        } else {
            SharedPreferenceUtil.saveSchoolMateList(MySchoolMateActivity.this, schoolMateModels);
            renderData(schoolMateModels);
            for (SchoolMateModel schoolMateModel : schoolMateModels) {
                int id = schoolMateModel.getToId();
                Log.e(TAG, "onNext:" + id);
                schoolMateIds.add(id);
            }
        }
        super.onResume();
    }
}
