package com.yetland.myfriend.view;

import com.yetland.myfriend.model.ActivityModel;
import com.yetland.myfriend.model.ResponseModel;

import java.util.List;

/**
 * Created by yeliang on 2016/4/19.
 */
public interface MainView extends LoadView {

    void hideMainView();

    void showMainView();

    void renderData(ResponseModel responseModel);

    void initHeaderView();

    void initDrawer();

    void initRecyclerView(List<ActivityModel> activityModels);

    void showToast(String message);

    void passwordError();
}
