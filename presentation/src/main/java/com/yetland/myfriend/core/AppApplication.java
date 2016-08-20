package com.yetland.myfriend.core;

import android.app.Application;
import android.content.Context;

import com.yetland.myfriend.core.dagger2.component.AppComponent;
import com.yetland.myfriend.core.dagger2.component.DaggerAppComponent;
import com.yetland.myfriend.core.dagger2.module.AppModule;

/**
 * Created by yeliang on 2016/4/15.
 */
public class AppApplication extends Application {

    private AppComponent appComponent;

    public static AppApplication get(Context context) {
        return (AppApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
