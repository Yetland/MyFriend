package com.yetland.myfriend.core.dagger2.module;

import android.app.Application;

import com.yetland.myfriend.core.AppApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yeliang on 2016/4/15.
 */
@Module
public class AppModule {

    private AppApplication application;

    public AppModule(AppApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    protected Application provideApplication() {
       return this.application;
    }
}

