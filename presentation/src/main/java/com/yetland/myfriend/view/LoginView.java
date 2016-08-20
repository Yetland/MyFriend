package com.yetland.myfriend.view;

import com.yetland.myfriend.model.UserModel;

/**
 * Created by yeliang on 2016/4/15.
 */
public interface LoginView extends LoadView {
    void gotoMainActivity();
    void gotoRegisterActivity(UserModel userModel);
}
