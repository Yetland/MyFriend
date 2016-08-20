package com.yetland.myfriend.view;

import com.yetland.myfriend.model.MessageModel;

/**
 * Created by yeliang on 2016/4/27.
 */
public interface MessageBoardView extends LoadView {
    void hideNoResult();

    void showNoResult();

    void showResult();

    void hideResult();

    void renderData(MessageModel messageModel);

}
