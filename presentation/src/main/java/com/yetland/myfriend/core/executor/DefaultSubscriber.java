package com.yetland.myfriend.core.executor;

import rx.Subscriber;

/**
 * Created by yeliang on 2016/3/28.
 * 未定义的订阅者，实现三个生命周期
 */
public class DefaultSubscriber<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(T t) {

    }
}
