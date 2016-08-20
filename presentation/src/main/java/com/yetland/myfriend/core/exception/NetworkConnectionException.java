package com.yetland.myfriend.core.exception;

/**
 * Created by yeliang on 2016/3/28.
 * 网络连接异常类
 */
public class NetworkConnectionException extends Exception {

    public NetworkConnectionException() {
    }

    public NetworkConnectionException(String detailMessage) {
        super(detailMessage);
    }

    public NetworkConnectionException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public NetworkConnectionException(Throwable throwable) {
        super(throwable);
    }
}
