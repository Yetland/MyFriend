package com.yetland.myfriend.core.exception;

/**
 * Created by yeliang on 2016/3/28.
 */
public interface ErrorBundle {
    Exception getException();

    String getErrorMessage();
}
