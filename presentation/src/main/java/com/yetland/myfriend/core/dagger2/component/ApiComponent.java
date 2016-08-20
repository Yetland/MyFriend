package com.yetland.myfriend.core.dagger2.component;

import com.yetland.myfriend.core.api.RestApi;
import com.yetland.myfriend.core.dagger2.ActivityScope;
import com.yetland.myfriend.view.activity.ActivityDetailActivity;
import com.yetland.myfriend.view.activity.CreateActivity;
import com.yetland.myfriend.view.activity.LeaveMessageActivity;
import com.yetland.myfriend.view.activity.LoginActivity;
import com.yetland.myfriend.view.activity.MainActivity;
import com.yetland.myfriend.view.activity.MessageBoardActivity;
import com.yetland.myfriend.view.activity.MyActivity;
import com.yetland.myfriend.view.activity.MySchoolMateActivity;
import com.yetland.myfriend.view.activity.NotifyActivity;
import com.yetland.myfriend.view.activity.SearchActivity;
import com.yetland.myfriend.view.activity.SearchSchoolMateActivity;
import com.yetland.myfriend.view.activity.UpdateUserInfoActivity;
import com.yetland.myfriend.view.activity.UserDetailActivity;
import com.yetland.myfriend.view.fragment.MyActivityFragment;
import com.yetland.myfriend.core.module.ApiModule;

import dagger.Component;

/**
 * Created by yeliang on 2016/4/15.
 */
@ActivityScope
@Component(modules = ApiModule.class, dependencies = AppComponent.class)
public interface ApiComponent {

    LoginActivity inject(LoginActivity activity);

    UpdateUserInfoActivity inject(UpdateUserInfoActivity activity);

    MainActivity inject(MainActivity activity);

    ActivityDetailActivity inject(ActivityDetailActivity detailActivity);

    MyActivity inject(MyActivity myActivity);

    MyActivityFragment inject(MyActivityFragment myActivityFragment);

    MySchoolMateActivity inject(MySchoolMateActivity mySchoolMateActivity);

    SearchSchoolMateActivity inject(SearchSchoolMateActivity searchSchoolMateActivity);

    UserDetailActivity inject(UserDetailActivity userDetailActivity);

    SearchActivity inject(SearchActivity searchActivity);

    NotifyActivity inject(NotifyActivity notifyActivity);

    MessageBoardActivity inject(MessageBoardActivity messageBoardActivity);

    CreateActivity inject(CreateActivity createActivity);

    LeaveMessageActivity inject(LeaveMessageActivity leaveMessageActivity);

    RestApi getApiService();
}
