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
import com.yetland.myfriend.model.MessageModel;
import com.yetland.myfriend.model.UserModel;
import com.yetland.myfriend.presenter.MessageBoardPresenter;
import com.yetland.myfriend.view.MessageBoardView;
import com.yetland.myfriend.view.adapter.MessageBoardAdapter;
import com.yetland.myfriend.core.dagger2.component.AppComponent;
import com.yetland.myfriend.core.dagger2.component.DaggerApiComponent;
import com.yetland.myfriend.core.module.ApiModule;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessageBoardActivity extends BaseActivity implements MessageBoardView {

    private static final String TAG = "MessageBoardActivity";
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.rv_message_board)
    RecyclerView rvMessageBoard;
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

    private UserModel userModel;
    private MessageBoardPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_board);
        ButterKnife.bind(this);

        userModel = new UserModel();
        userModel = (UserModel) getIntent().getSerializableExtra("user");
        if (userModel != null) {
            toolbar.setTitle(" ");
            tvTitle.setText("我的留言板");

            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(view -> onBackPressed());

            presenter = new MessageBoardPresenter(restApi, this, this);
            presenter.getMessageBoard(userModel.getId());
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
        loadViewTvNoResult.setText("没有留言");
    }

    public void hideNoResult() {
        loadViewTvNoResult.setVisibility(View.INVISIBLE);
    }

    public void showResult() {
        rvMessageBoard.setVisibility(View.VISIBLE);
    }

    public void hideResult() {
        rvMessageBoard.setVisibility(View.INVISIBLE);
    }

    public void showErrorMessage(String errorMessage) {
        loadViewTvErrorMessage.setText(errorMessage);
    }

    public Context getContext() {
        return MessageBoardActivity.this;
    }

    public void renderData(MessageModel messageModel) {

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayout.VERTICAL);

        rvMessageBoard.setLayoutManager(manager);
        MessageBoardAdapter adapter = new MessageBoardAdapter(messageModel);
        rvMessageBoard.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener(position -> {
            //TODO
            Log.e(TAG, "renderData:" + position);
        });
        adapter.setOnReplyClickListener(position -> {
            Toast.makeText(MessageBoardActivity.this, "回复留言:"+position, Toast.LENGTH_SHORT).show();
        });
    }

    @OnClick(R.id.load_view_bt_retry)
    public void onClick() {
        presenter.getMessageBoard(userModel.getId());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}

