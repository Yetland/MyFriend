package com.yetland.domain.core.exception;

/**
 * @author yeliang
 * @data 2016/6/28.
 */
public class DefaultErrorBundle implements ErrorBundle {


    final String DEFAULT_ERROR_MESSAGE = "UNKNOWN ERROR";
    Exception exception;

    public DefaultErrorBundle(Exception exception) {
        this.exception = exception;
    }

    @Override
    public Exception getException() {
        return exception;
    }

    @Override
    public String getErrorMessage() {
        return (exception != null) ? exception.getMessage() : DEFAULT_ERROR_MESSAGE;
    }
}
