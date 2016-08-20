package com.yetland.myfriend.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.yetland.myfriend.R;
import com.yetland.myfriend.core.view.activity.BaseActivity;
import com.yetland.myfriend.core.api.RestApi;
import com.yetland.myfriend.model.ActivityModel;
import com.yetland.myfriend.model.SearchUserModel;
import com.yetland.myfriend.presenter.SearchActivityPresenter;
import com.yetland.myfriend.view.SearchView;
import com.yetland.myfriend.view.adapter.ActivityAdapter;
import com.yetland.myfriend.core.dagger2.component.AppComponent;
import com.yetland.myfriend.core.dagger2.component.DaggerApiComponent;
import com.yetland.myfriend.core.module.ApiModule;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity implements SearchView {

    private static final String TAG = "SearchActivity";
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.et_search)
    EditText etSearch;
    @Bind(R.id.bt_search)
    Button btSearch;
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
    @Bind(R.id.loading_view)
    LinearLayout loadingView;
    @Bind(R.id.load_view_tv_no_result)
    TextView loadViewTvNoResult;
    @Bind(R.id.rv_result)
    RecyclerView rvResult;
    @Inject
    RestApi restApi;
    private SearchActivityPresenter presenter;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        presenter = new SearchActivityPresenter(restApi, this, this);

        RxTextView.textChanges(etSearch).subscribe(charSequence -> {
            title = charSequence.toString();
            title = title.replace(" ", "");
            if (!title.equals(""))
                btSearch.setEnabled(true);
            else
                btSearch.setEnabled(false);
        });
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
    public void showLoading() {
        loadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loadingView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showRetry() {
        retryView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry() {
        retryView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        loadViewTvErrorMessage.setText(errorMessage);
    }

    @Override
    public Context getContext() {
        return SearchActivity.this;
    }

    @Override
    public void showNoResult() {
        loadViewTvNoResult.setVisibility(View.VISIBLE);
        loadViewTvNoResult.setText("没有找到相关结果");
    }

    @Override
    public void hideNoResult() {
        loadViewTvNoResult.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showResult() {
        rvResult.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideResult() {
        rvResult.setVisibility(View.INVISIBLE);
    }

    @Override
    public void renderSearchUserModel(List<SearchUserModel> searchUserModels) {
    }

    @Override
    public void renderActivityModel(List<ActivityModel> activityModels) {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayout.VERTICAL);

        rvResult.setLayoutManager(manager);
        ActivityAdapter adapter = new ActivityAdapter(activityModels);
        rvResult.setAdapter(adapter);

        adapter.setOnItemClickListener(position -> {
            Log.e(TAG, "renderData:" + position);
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("activityModel",activityModels.get(position));

            intent.putExtras(bundle);
            //TODO
            intent.setClass(SearchActivity.this,ActivityDetailActivity.class);

            startActivity(intent);
        });
    }

    @OnClick({R.id.iv_back, R.id.bt_search, R.id.load_view_bt_retry})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.bt_search:
                presenter.searchActivity(title);
                break;
            case R.id.load_view_bt_retry:
                presenter.searchActivity(title);
                break;
        }
    }
}
