package com.yetland.myfriend.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.yetland.myfriend.R;
import com.yetland.myfriend.core.view.activity.BaseActivity;
import com.yetland.myfriend.presenter.WelcomeActivityPresenter;
import com.yetland.myfriend.view.WelcomeActivityView;
import com.yetland.myfriend.core.dagger2.component.AppComponent;

public class WelcomeActivity extends BaseActivity implements WelcomeActivityView{

    private WelcomeActivityPresenter presenter;
    private static final String TAG = "WelcomeActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        presenter = new WelcomeActivityPresenter(WelcomeActivity.this,this);
        presenter.init();
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void gotoMainActivity() {
        Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void gotoLogin() {
        Intent intent = new Intent(WelcomeActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showErrorMessage(String errorMessage) {

    }

    @Override
    public Context getContext() {
        return null;
    }
}
