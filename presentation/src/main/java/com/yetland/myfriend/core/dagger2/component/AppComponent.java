package com.yetland.myfriend.core.dagger2.component;

import android.app.Application;

import com.yetland.myfriend.core.dagger2.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by yeliang on 2016/4/15.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    Application getApplication();

}
