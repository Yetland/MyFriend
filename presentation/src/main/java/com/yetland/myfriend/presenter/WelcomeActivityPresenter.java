package com.yetland.myfriend.presenter;

import com.yetland.myfriend.model.UserModel;
import com.yetland.myfriend.util.CustomUtils;
import com.yetland.myfriend.util.SharedPreferenceUtil;
import com.yetland.myfriend.view.WelcomeActivityView;
import com.yetland.myfriend.view.activity.WelcomeActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yeliang on 2016/4/15.
 */
public class WelcomeActivityPresenter {

    private WelcomeActivity welcomeActivity;
    private WelcomeActivityView welcomeActivityView;

    public WelcomeActivityPresenter(WelcomeActivity welcomeActivity, WelcomeActivityView welcomeActivityView) {
        this.welcomeActivity = welcomeActivity;
        this.welcomeActivityView = welcomeActivityView;
    }

    public void init() {

        UserModel user = (UserModel) SharedPreferenceUtil.readObject(welcomeActivity, CustomUtils.key.USER_INFO, CustomUtils.fileName.USER);
        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        if (user != null) {
                            welcomeActivityView.gotoMainActivity();
                        } else {
                            welcomeActivityView.gotoLogin();
                        }
                    }
                }, 2000);
    }
}
