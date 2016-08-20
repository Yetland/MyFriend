package com.yetland.myfriend.presenter;

/**
 * Created by yeliang on 2016/3/28.
 */
public interface BasePresenter {
    void resume();

    void pause();

    void destroy();

    /**
     * 加载失败的界面控制
     */
    void loadFailure();

    /**
     * 加载成功的界面控制
     */
    void loadSuccess();

    /**
     * 加载中的界面控制
     */
    void loading();
}
