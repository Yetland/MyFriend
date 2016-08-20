package com.yetland.myfriend.view;

import com.yetland.myfriend.model.ActivityModel;

import java.util.List;

/**
 * Created by yeliang on 2016/4/21.
 */
public interface MyActivityListView extends LoadView {

    void renderData(List<ActivityModel> activityModels);

    void showNoResult();

    void hideNoResult();

    void showResult();

    void hideResult();

}
