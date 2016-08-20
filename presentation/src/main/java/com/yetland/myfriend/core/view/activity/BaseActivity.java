package com.yetland.myfriend.core.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yetland.myfriend.core.AppApplication;
import com.yetland.myfriend.core.dagger2.component.AppComponent;
import com.yetland.myfriend.core.module.ApiModule;


/**
 * Created by yeliang on 2016/3/28.
 */
public abstract class BaseActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActivityComponent(AppApplication.get(this).getAppComponent());
    }

    protected abstract void setupActivityComponent(AppComponent appComponent);

    protected ApiModule getApiModule() {
        return new ApiModule(this);
    }

}
