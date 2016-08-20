package com.yetland.myfriend.core.dagger2.module;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.yetland.myfriend.core.api.RestApi;
import com.yetland.myfriend.core.dagger2.ActivityScope;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yeliang on 2016/4/15.
 */
@Module
public class ApiModule {


    private Activity activity;

    private Fragment fragment;

    public ApiModule(Activity activity) {
        this.activity = activity;
    }

    public ApiModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @ActivityScope
    protected Fragment provideFragment() {
        return fragment;
    }

    @Provides
    @ActivityScope
    protected Activity ProvideActivity() {
        return activity;
    }

    @Provides
    @ActivityScope
    protected RestApi provideApiService() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RestApi.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(RestApi.class);
    }
}
