package com.yetland.myfriend.view;

import android.content.Context;

/**
 * Created by yeliang on 2016/4/15.
 */
public interface LoadView {

    void showLoading();
    void hideLoading();
    void showRetry();
    void hideRetry();
    void showErrorMessage(String errorMessage);
    Context getContext();
}
