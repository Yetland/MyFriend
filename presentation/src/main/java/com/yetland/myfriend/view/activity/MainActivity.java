package com.yetland.myfriend.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yetland.myfriend.R;
import com.yetland.myfriend.core.view.activity.BaseActivity;
import com.yetland.myfriend.core.data.ResultCodeList;
import com.yetland.myfriend.core.api.RestApi;
import com.yetland.myfriend.model.ActivityModel;
import com.yetland.myfriend.model.ImageItem;
import com.yetland.myfriend.model.ResponseModel;
import com.yetland.myfriend.model.UserModel;
import com.yetland.myfriend.presenter.MainActivityPresenter;
import com.yetland.myfriend.util.BaseTools;
import com.yetland.myfriend.util.CustomUtils;
import com.yetland.myfriend.util.SharedPreferenceUtil;
import com.yetland.myfriend.view.DividerItemDecoration;
import com.yetland.myfriend.view.MainView;
import com.yetland.myfriend.view.adapter.ActivityAdapter;
import com.yetland.myfriend.view.adapter.MainActivityPagerAdapter;
import com.yetland.myfriend.core.dagger2.component.AppComponent;
import com.yetland.myfriend.core.dagger2.component.DaggerApiComponent;
import com.yetland.myfriend.core.module.ApiModule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainView {

    private static final String TAG = "MainActivity";
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.vp_image)
    ViewPager vpImage;
    @Bind(R.id.ll_dot)
    LinearLayout llDot;
    @Bind(R.id.nav_view)
    NavigationView navView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;
    @Bind(R.id.rl_image)
    RelativeLayout rlImage;
    @Bind(R.id.rv_activity_list)
    RecyclerView rvActivityList;
    @Bind(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;

    @Bind(R.id.ll_main_view)
    LinearLayout llMainView;
    @Bind(R.id.tv_title)
    TextView tvActivityTitle;// 标题
    @Inject
    RestApi restApi;
    @Bind(R.id.pg_loading)
    ProgressBar pgLoading;
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

    private UserModel userModel;
    private int mDotIndex = 0;// 当前选中的点
    private int imageSize = 0;// 图片数量
    private int mScreenWidth;// 屏幕宽度
    private View mHeaderView;// 导航栏的头部view
    private ActivityAdapter adapter;
    private MainActivityPresenter presenter;
    private List<ActivityModel> activityModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate:");
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);// 绑定

        toolbar.setTitle("");// 截取toolbar，设置标题为空
        tvActivityTitle.setText("首页");// 设置标题
        setSupportActionBar(toolbar);
        mScreenWidth = BaseTools.getWindowsWidth(MainActivity.this);// 获取屏幕宽度

        initDrawer();// 初始化侧滑栏
        initHeaderView();//初始化侧栏头部
        presenter = new MainActivityPresenter(restApi, this, MainActivity.this);

        // 设置刷新
        srlRefresh.setOnRefreshListener(() -> {
            presenter.getMainData(userModel);// 重新加载
        });
        srlRefresh.setRefreshing(true);
        presenter.getMainData(userModel);// 获取数据
    }

    //TODO 返回内容的判断
    @Override
    public void renderData(ResponseModel responseModel) {

        pgLoading.setVisibility(View.INVISIBLE);

        List<ActivityModel> activityModels;
        List<ImageItem> imageItems;
        imageItems = responseModel.getContent().getImageItems();
        imageSize = imageItems.size();
        initDots(imageSize);
        initImageLayout(imageItems);
        activityModels = responseModel.getContent().getActivityModels();
        if (activityModels.size() == 0) {
            showToast("没有数据");
        } else
            Collections.reverse(activityModels);
            initRecyclerView(activityModels);
    }

    /**
     * 初始化活动列表
     *
     * @param activityModels
     */
    @Override
    public void initRecyclerView(List<ActivityModel> activityModels) {
        Log.e(TAG, "initRecyclerView:");
        this.activityModels = activityModels;
        // 设置layout的方向
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvActivityList.setLayoutManager(manager);
        rvActivityList.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL_LIST));
        adapter = new ActivityAdapter(this.activityModels);
        adapter.notifyDataSetChanged();
        rvActivityList.setAdapter(adapter);
        adapter.setOnItemClickListener(position -> {
            Intent intent = new Intent(MainActivity.this, ActivityDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("activityModel", activityModels.get(position));
            intent.putExtras(bundle);

            startActivityForResult(intent,1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ResultCodeList.DELETE_SUCCESS){
            Log.e(TAG, "onActivityResult:" + "deleteSuccess");
            ActivityModel activityModel = (ActivityModel) data.getSerializableExtra("activityModel");
            activityModels.remove(activityModel);
            initRecyclerView(activityModels);
        }
    }

    @Override
    public void initHeaderView() {
        Log.e(TAG, "initHeaderView:");
        // 从内存获取当前User的信息
        userModel = (UserModel) SharedPreferenceUtil.readObject(MainActivity.this,
                CustomUtils.key.USER_INFO,
                CustomUtils.fileName.USER);

        mHeaderView = navView.getHeaderView(0);// 重要！！！
        TextView nick = (TextView) mHeaderView.findViewById(R.id.nick);
        nick.setText(userModel.getNick());
        TextView sign = (TextView) mHeaderView.findViewById(R.id.sign);
        sign.setText(userModel.getSign());
        // 跳转到更新用户信息
        nick.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, MyInfoActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", userModel);
            intent.putExtras(bundle);
            startActivity(intent);

            drawer.closeDrawer(GravityCompat.START);
        });
    }

    // 初始化侧滑栏
    @Override
    public void initDrawer() {
        Log.e(TAG, "initDrawer:");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navView.setNavigationItemSelectedListener(this);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", userModel);
        if (id == R.id.nav_my_school_mate) {
            intent = new Intent(MainActivity.this, MySchoolMateActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (id == R.id.nav_notification) {
            intent = new Intent(MainActivity.this, NotifyActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (id == R.id.nav_message_board) {
            intent = new Intent(MainActivity.this, MessageBoardActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (id == R.id.nav_activity) {
            intent = new Intent(MainActivity.this, MyActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (id == R.id.nav_share) {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        } else if (id == R.id.nav_send) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            SharedPreferenceUtil.cleanData(getContext(), CustomUtils.fileName.USER);
            finish();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // 初始化白点
    private void initDots(int imageSize) {
        Log.e(TAG, "initDots:");
        llDot.removeAllViews();
        for (int i = 0; i < imageSize; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.rightMargin = 8;
            ImageView imgDot = new ImageView(MainActivity.this);
            imgDot.setId(i);
            imgDot.setImageDrawable(getResources().getDrawable(R.drawable.dot));
            if (mDotIndex == i) {
                imgDot.setSelected(true);
            }
            llDot.addView(imgDot, i, params);
        }
    }

    // 设置当前显示的白点
    private void setDot(int position, int imageSize) {
        mDotIndex = position;
        initDots(imageSize);
    }

    // 图片显示的初始化
    private void initImageLayout(List<ImageItem> imageItems) {
        Log.e(TAG, "initImageLayout:");
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.height = (int) ((mScreenWidth * 9.0) / 16.0);
        params.width = mScreenWidth;
        rlImage.setLayoutParams(params);

        List<String> imageUrl = new ArrayList<>();

        for (int i = 0; i < imageSize + 2; i++) {
            if (i == 0) {
                imageUrl.add(RestApi.BASE_URL + "TeamClock/image/" + imageItems.get(imageSize - 1).getImageId());
            } else if (i == imageSize + 1) {
                imageUrl.add(RestApi.BASE_URL + "TeamClock/image/" + imageItems.get(0).getImageId());
            } else {
                imageUrl.add(RestApi.BASE_URL + "TeamClock/image/" + imageItems.get(i - 1).getImageId());
            }
        }

        MainActivityPagerAdapter adapter = new MainActivityPagerAdapter(MainActivity.this, imageUrl);
        vpImage.setAdapter(adapter);
        vpImage.setCurrentItem(1);
        vpImage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    setDot(imageSize - 1, imageSize);
                    vpImage.setCurrentItem(imageSize, false);
                    return;
                }
                if (position == imageSize + 1) {
                    setDot(0, imageSize);
                    vpImage.setCurrentItem(1, false);
                    return;
                }
                setDot(position - 1, imageSize);
                vpImage.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void hideMainView() {
        llMainView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showMainView() {
        llMainView.setVisibility(View.VISIBLE);
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
        srlRefresh.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        pgLoading.setVisibility(View.INVISIBLE);
        srlRefresh.setRefreshing(false);
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
    public void showToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void passwordError() {
        showToast("账号或密码不正确");
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public Context getContext() {
        return MainActivity.this;
    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        initHeaderView();
        super.onResume();
    }

    @OnClick(R.id.load_view_bt_retry)
    public void onClick() {
        presenter.getMainData(userModel);
    }
}
