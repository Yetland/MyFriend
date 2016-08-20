package com.yetland.myfriend.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.yetland.myfriend.R;
import com.yetland.myfriend.core.view.activity.BaseActivity;
import com.yetland.myfriend.core.api.RestApi;
import com.yetland.myfriend.model.SchoolMateInvitation;
import com.yetland.myfriend.model.UserModel;
import com.yetland.myfriend.presenter.NotifyPresenter;
import com.yetland.myfriend.view.widget.CustomProgressDialog;
import com.yetland.myfriend.view.NotifyView;
import com.yetland.myfriend.view.adapter.NotifyAdapter;
import com.yetland.myfriend.core.dagger2.component.AppComponent;
import com.yetland.myfriend.core.dagger2.component.DaggerApiComponent;
import com.yetland.myfriend.core.module.ApiModule;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotifyActivity extends BaseActivity implements NotifyView {

    private static final String TAG = "NotifyActivity";
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.rv_invitation)
    RecyclerView rvInvitation;
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

    List<SchoolMateInvitation> schoolMateInvitations;
    private UserModel userModel;
    private NotifyPresenter presenter;
    private CustomProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
        ButterKnife.bind(this);

        userModel = new UserModel();
        userModel = (UserModel) getIntent().getSerializableExtra("user");
        if (userModel != null) {
            toolbar.setTitle(" ");
            tvTitle.setText("我的通知");

            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(view -> onBackPressed());

            presenter = new NotifyPresenter(restApi, this, this);
            presenter.getSchoolMateInvitation(userModel.getId());
        } else {
            finish();
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
        loadViewTvNoResult.setText("没有通知");
    }

    public void hideNoResult() {
        loadViewTvNoResult.setVisibility(View.INVISIBLE);
    }

    public void showResult() {
        rvInvitation.setVisibility(View.VISIBLE);
    }

    public void hideResult() {
        rvInvitation.setVisibility(View.INVISIBLE);
    }

    public void showErrorMessage(String errorMessage) {
        loadViewTvErrorMessage.setText(errorMessage);
    }

    public Context getContext() {
        return NotifyActivity.this;
    }

    public void renderData(List<SchoolMateInvitation> schoolMateInvitations) {

        this.schoolMateInvitations = schoolMateInvitations;
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayout.VERTICAL);

        rvInvitation.setLayoutManager(manager);
        NotifyAdapter adapter = new NotifyAdapter(this.schoolMateInvitations);
        rvInvitation.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener(position -> {
            //TODO
            Log.e(TAG, "renderData:" + position);
        });
        adapter.setOnAcceptClickListener(position -> {
            //TODO
            Log.e(TAG, "renderData:" + position);
            // 此处fromId和toId 都可以从schoolMateInvitation里取
            presenter.acceptSchoolMateInvitation(schoolMateInvitations.get(position).getFromId(), userModel.getId(),position);
        });
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(NotifyActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDialog(String content) {
        dialog = CustomProgressDialog.show(getContext(), content, false, null);
        dialog.show();
    }

    @Override
    public void hideDialog() {
        dialog.dismiss();
    }

    @Override
    public void acceptInvitationSuccess(int position) {
        schoolMateInvitations.get(position).setStatus("done");
        renderData(schoolMateInvitations);
    }

    @OnClick(R.id.load_view_bt_retry)
    public void onClick() {
        presenter.getSchoolMateInvitation(userModel.getId());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
