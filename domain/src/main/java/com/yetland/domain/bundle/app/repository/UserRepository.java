package com.yetland.domain.bundle.app.repository;

import rx.Observable;

/**
 * @author yeliang
 * @data 2016/6/29.
 */
public interface UserRepository {
    Observable login(String phoneNumber,String password);
}
