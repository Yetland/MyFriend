package com.yetland.myfriend.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yetland.myfriend.R;
import com.yetland.myfriend.core.view.fragment.BaseFragment;
import com.yetland.myfriend.core.data.ResultCodeList;
import com.yetland.myfriend.core.api.RestApi;
import com.yetland.myfriend.model.ActivityModel;
import com.yetland.myfriend.model.UserModel;
import com.yetland.myfriend.presenter.MyActivityPresenter;
import com.yetland.myfriend.util.CustomUtils;
import com.yetland.myfriend.util.SharedPreferenceUtil;
import com.yetland.myfriend.view.DividerItemDecoration;
import com.yetland.myfriend.view.MyActivityListView;
import com.yetland.myfriend.view.activity.ActivityDetailActivity;
import com.yetland.myfriend.view.adapter.ActivityAdapter;
import com.yetland.myfriend.core.dagger2.component.AppComponent;
import com.yetland.myfriend.core.dagger2.component.DaggerApiComponent;
import com.yetland.myfriend.core.module.ApiModule;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MyActivityFragment extends BaseFragment implements MyActivityListView {

    private static final String TAG = "MyActivityFragment";

    @Bind(R.id.rv_activity_list)
    RecyclerView rvActivityList;
    @Inject
    RestApi restApi;
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

    private String type;
    private UserModel userModel;
    private MyActivityPresenter presenter;
    private List<ActivityModel> activityModels;
    public static MyActivityFragment newInstance(String type) {
        Bundle args = new Bundle();
        MyActivityFragment fragment = new MyActivityFragment();
        args.putString("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_activity_list, container, false);
        ButterKnife.bind(this, view);

        type = getArguments().getString("type");
        userModel = (UserModel) SharedPreferenceUtil.readObject(getActivity(), CustomUtils.key.USER_INFO,
                CustomUtils.fileName.USER);

        presenter = new MyActivityPresenter(restApi, getActivity(), this, type, userModel);
        presenter.getMyActivity();

        return view;
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
    public void renderData(List<ActivityModel> activityModels) {
        if (activityModels.size() == 0) {
            showNoResult();
            hideLoading();
            hideRetry();
            hideResult();
        } else {
            this.activityModels = activityModels;
            Log.e(TAG, "renderData:" + activityModels.toString());
            Collections.reverse(this.activityModels);
            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            rvActivityList.setLayoutManager(manager);
            rvActivityList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
            ActivityAdapter adapter = new ActivityAdapter(this.activityModels);
            adapter.notifyDataSetChanged();
            rvActivityList.setAdapter(adapter);
            adapter.setOnItemClickListener(position -> {
                Intent intent = new Intent(getActivity(), ActivityDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("activityModel", activityModels.get(position));
                intent.putExtras(bundle);

                startActivityForResult(intent,1);
            });
        }
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
    public void showNoResult() {
        loadViewTvNoResult.setVisibility(View.VISIBLE);
        loadViewTvNoResult.setText("没有活动");
    }

    @Override
    public void hideNoResult() {
        loadViewTvNoResult.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showResult() {
        rvActivityList.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideResult() {
        rvActivityList.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        loadViewTvErrorMessage.setText(errorMessage);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.load_view_bt_retry)
    public void onClick() {
        presenter.getMyActivity();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            Log.e(TAG, "onActivityResult:" + resultCode);
            if (resultCode == ResultCodeList.DELETE_SUCCESS) {
                ActivityModel activityModel = (ActivityModel) data.getSerializableExtra("activityModel");
                activityModels.remove(activityModel);
                renderData(activityModels);
        }
    }
}