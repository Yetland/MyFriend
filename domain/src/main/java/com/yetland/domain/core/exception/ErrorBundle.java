package com.yetland.domain.core.exception;

/**
 * @author yeliang
 * @data 2016/6/28.
 */
public interface ErrorBundle {
    Exception getException();
    String getErrorMessage();
}
