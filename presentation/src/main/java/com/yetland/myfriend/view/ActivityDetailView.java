package com.yetland.myfriend.view;

import com.yetland.myfriend.model.ActivityMember;

import java.util.List;

/**
 * Created by yeliang on 2016/4/20.
 */
public interface ActivityDetailView extends LoadView {

    void renderData(List<ActivityMember> activityMembers);

    void showRecyclerView();

    void hideRecyclerView();

    void showDialog(String message);

    void hideDialog();

    void showToast(String message);

    void joinActivitySuccess(List<ActivityMember> activityMembers);

    void quitActivitySuccess(List<ActivityMember> activityMembers);

    void dismissActivitySuccess();
}
