package com.yetland.myfriend.view;

import com.yetland.myfriend.model.ActivityModel;
import com.yetland.myfriend.model.SearchUserModel;

import java.util.List;

/**
 * Created by yeliang on 2016/4/25.
 */
public interface SearchView  extends LoadView{
    void showNoResult();
    void hideNoResult();
    void showResult();
    void hideResult();

    void renderSearchUserModel(List<SearchUserModel> searchUserModels);

    void renderActivityModel(List<ActivityModel> activityModels);
}
