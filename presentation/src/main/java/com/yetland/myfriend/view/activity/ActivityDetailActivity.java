package com.yetland.myfriend.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yetland.myfriend.R;
import com.yetland.myfriend.core.view.activity.BaseActivity;
import com.yetland.myfriend.core.data.ResultCodeList;
import com.yetland.myfriend.core.api.RestApi;
import com.yetland.myfriend.core.data.transform.Transform;
import com.yetland.myfriend.databinding.ActivityDetailBinding;
import com.yetland.myfriend.model.ActivityMember;
import com.yetland.myfriend.model.ActivityModel;
import com.yetland.myfriend.model.SchoolMateModel;
import com.yetland.myfriend.model.UserModel;
import com.yetland.myfriend.presenter.UserItemPresenter;
import com.yetland.myfriend.util.CustomUtils;
import com.yetland.myfriend.util.SharedPreferenceUtil;
import com.yetland.myfriend.view.ActivityDetailView;
import com.yetland.myfriend.view.widget.CustomProgressDialog;
import com.yetland.myfriend.view.adapter.ActivityMemberAdapter;
import com.yetland.myfriend.core.dagger2.component.AppComponent;
import com.yetland.myfriend.core.dagger2.component.DaggerApiComponent;
import com.yetland.myfriend.core.module.ApiModule;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityDetailActivity extends BaseActivity implements ActivityDetailView {

    private static final String TAG = "ActivityDetailActivity";
    ActivityDetailBinding binding;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_title)
    TextView tvTitle;// 标题
    @Bind(R.id.creator_detail)
    TextView creatorDetail;// 创建者明细，如多少届、多少班等
    @Bind(R.id.rv_activity_member)
    RecyclerView rvActivityMember;// 活动成员List
    @Bind(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;// 下拉刷新组件
    @Bind(R.id.load_view_bt_retry)
    Button loadViewBtRetry;
    @Bind(R.id.load_view_tv_error_message)
    TextView loadViewTvErrorMessage;
    @Bind(R.id.retry_view)
    LinearLayout retryView;
    @Bind(R.id.loading_view)
    LinearLayout loadingView;

    @Bind(R.id.bt_join_activity)
    Button btJoinActivity;
    @Bind(R.id.bt_quit_activity)
    Button btQuitActivity;
    @Bind(R.id.bt_dismiss_activity)
    Button btDismissActivity;

    @Inject
    RestApi restApi;

    private ActivityMemberAdapter adapter;
    private UserItemPresenter presenter;
    private ActivityModel activityModel;

    private UserModel userModel;
    private CustomProgressDialog dialog;
    private boolean creator = false;
    private boolean member = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        View mView = binding.getRoot();
        ButterKnife.bind(this, mView);

        toolbar.setTitle(" ");
        tvTitle.setText("活动详情");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> this.onBackPressed());

        Intent intent = getIntent();
        activityModel = (ActivityModel) intent.getSerializableExtra("activityModel");
        userModel = (UserModel) SharedPreferenceUtil.readObject(getContext(), CustomUtils.key.USER_INFO, CustomUtils.fileName.USER);

        if (activityModel != null) {
            binding.setActivityModel(activityModel);
            creatorDetail.setText(activityModel.getGraduateYear() + " 届 " + activityModel.getClassName());

            loadingView.setVisibility(View.VISIBLE);
            presenter = new UserItemPresenter(restApi, ActivityDetailActivity.this, this);

            presenter.getActivityMember(activityModel.getActivityId());

            srlRefresh.setOnRefreshListener(() -> {
                presenter.getActivityMember(activityModel.getActivityId());

                btDismissActivity.setVisibility(View.GONE);
                btJoinActivity.setVisibility(View.GONE);
                btQuitActivity.setVisibility(View.GONE);

            });

        } else {
            Toast.makeText(ActivityDetailActivity.this, "载入出错...", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerApiComponent.builder()
                .appComponent(appComponent)
                .apiModule(new ApiModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void renderData(List<ActivityMember> activityMembers) {
        Log.e(TAG, "renderData:" + activityMembers.toString());
        loadingView.setVisibility(View.INVISIBLE);

        // 重新设置
        setMember(false);
        setCreator(false);
        btDismissActivity.setVisibility(View.GONE);
        btQuitActivity.setVisibility(View.GONE);
        btJoinActivity.setVisibility(View.GONE);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        adapter = new ActivityMemberAdapter(activityMembers, userModel);
        rvActivityMember.setLayoutManager(manager);
        rvActivityMember.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        for (ActivityMember activityMember : activityMembers) {

            // 如果是成员的ID和当前用户对比成功，则是当前用户是活动成员
            if (activityMember.getMemberId() == userModel.getId()) {
                setMember(true);
            }
            // 如果当前的活动的创建者ID和当前用户的ID一样，则当前用户是创建者
            if (activityMember.getCreatorId() == userModel.getId()) {
                setCreator(true);
            }
        }

        // 创建者拥有最高的权利
        if (isCreator()) {
            btDismissActivity.setVisibility(View.VISIBLE);
        } else if (isMember()) {
            btQuitActivity.setVisibility(View.VISIBLE);
        } else {
            btJoinActivity.setVisibility(View.VISIBLE);
        }

        adapter.setOnItemClickListener(position -> {
            Log.e(TAG, "renderData:" + position);
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            SchoolMateModel schoolMateModel = new SchoolMateModel();
            // 用户详细的信息显示的格式为SchoolMateModel,所以别的类型的需要转换
            schoolMateModel = Transform.ActivityMemberToSchoolMate(activityMembers.get(position));
            bundle.putSerializable("activityModel", activityModel);
            bundle.putSerializable("schoolMateModel", schoolMateModel);
            bundle.putSerializable("userModel", userModel);

            intent.putExtras(bundle);
            intent.setClass(ActivityDetailActivity.this, UserDetailActivity.class);

            startActivity(intent);
        });
    }

    @OnClick({R.id.load_view_bt_retry, R.id.bt_join_activity, R.id.bt_quit_activity, R.id.bt_dismiss_activity})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_join_activity:
                presenter.joinActivity(activityModel.getActivityId(), activityModel.getCreatorId(), userModel.getId());
                break;
            case R.id.bt_quit_activity:
                presenter.quitActivity(activityModel.getActivityId(), activityModel.getCreatorId(), userModel.getId());
                break;
            case R.id.bt_dismiss_activity:
                presenter.dismissActivity(activityModel);
                break;
            case R.id.load_view_bt_retry:
                presenter.getActivityMember(activityModel.getActivityId());
                break;
        }
    }

    @Override
    public void showRecyclerView() {
        rvActivityMember.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRecyclerView() {
        Log.e(TAG, "hideRecyclerView:");
        rvActivityMember.setVisibility(View.GONE);
    }

    @Override
    public void showDialog(String message) {
        dialog = CustomProgressDialog.show(getContext(), message, false, null);
        dialog.show();
    }

    @Override
    public void hideDialog() {
        dialog.dismiss();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(ActivityDetailActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void joinActivitySuccess(List<ActivityMember> activityMembers) {
        renderData(activityMembers);
    }

    @Override
    public void quitActivitySuccess(List<ActivityMember> activityMembers) {
        renderData(activityMembers);
    }

    @Override
    public void dismissActivitySuccess() {
        showToast("解散活动成功");
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("activityModel",activityModel);
        intent.putExtras(bundle);
        setResult(ResultCodeList.DELETE_SUCCESS,intent);
        finish();
    }

    @Override
    public void showLoading() {
        srlRefresh.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        srlRefresh.setRefreshing(false);
        loadingView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showRetry() {
        retryView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry() {
        retryView.setVisibility(View.GONE);
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        Log.e(TAG, "showErrorMessage:" + errorMessage);
        loadViewTvErrorMessage.setText(errorMessage);
    }

    @Override
    public Context getContext() {
        return ActivityDetailActivity.this;
    }

    public boolean isCreator() {
        return creator;
    }

    public void setCreator(boolean creator) {
        this.creator = creator;
    }

    public boolean isMember() {
        return member;
    }

    public void setMember(boolean member) {
        this.member = member;
    }

}
