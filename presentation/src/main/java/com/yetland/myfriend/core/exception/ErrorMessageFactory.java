package com.yetland.myfriend.core.exception;

import android.content.Context;

/**
 * Created by yeliang on 2016/3/28.
 */
public class ErrorMessageFactory {
    public ErrorMessageFactory() {
    }

    /**
     * ErrorMessage工厂、返回错误类型
     *
     * @param context
     * @param exception
     * @return
     */
    public static String creat(Context context, Exception exception) {
        String msg = exception.getMessage();
        if (exception instanceof NetworkConnectionException) {
                msg = "无网络连接";
        }
        return msg;
    }
}
