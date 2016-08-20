package com.yetland.myfriend.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.yetland.myfriend.R;
import com.yetland.myfriend.core.view.activity.BaseActivity;
import com.yetland.myfriend.core.data.ResultCodeList;
import com.yetland.myfriend.core.api.RestApi;
import com.yetland.myfriend.model.UserModel;
import com.yetland.myfriend.view.adapter.ActivityPagerAdapter;
import com.yetland.myfriend.core.dagger2.component.AppComponent;
import com.yetland.myfriend.core.dagger2.component.DaggerApiComponent;
import com.yetland.myfriend.view.fragment.MyActivityFragment;
import com.yetland.myfriend.core.module.ApiModule;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Inject
    RestApi restApi;
    private MyActivityFragment myCreateActivity;
    private MyActivityFragment myJoinedActivity;
    private ActivityPagerAdapter adapter;
    private UserModel userModel;
    private static final String TAG = "MyActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        ButterKnife.bind(this);

        userModel = (UserModel) getIntent().getSerializableExtra("userModel");

        toolbar.setTitle(" ");
        tvTitle.setText("我的活动");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        tabLayout.addTab(tabLayout.newTab().setText("我创建的活动"));
        tabLayout.addTab(tabLayout.newTab().setText("我加入的活动"));
        adapter = new ActivityPagerAdapter(getSupportFragmentManager());

        myCreateActivity = MyActivityFragment.newInstance("myCreateActivity");
        adapter.addFragment(myCreateActivity, "我创建的活动");
        myJoinedActivity = MyActivityFragment.newInstance("myJoinedActivity");
        adapter.addFragment(myJoinedActivity, "我加入的活动");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_my_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("userModel", userModel);
        intent.putExtras(bundle);

        switch (item.getItemId()) {
            case R.id.searchActivity:
                intent.setClass(MyActivity.this, SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.createActivity:
                intent.setClass(MyActivity.this, CreateActivity.class);
                startActivityForResult(intent, ResultCodeList.CREATE_SUCCESS);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult:" + resultCode);
        if (resultCode == ResultCodeList.CREATE_SUCCESS) {
            myCreateActivity = MyActivityFragment.newInstance("myCreateActivity");
            viewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPager);
        }
    }
}
