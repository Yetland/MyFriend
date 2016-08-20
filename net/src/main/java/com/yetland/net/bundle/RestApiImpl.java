package com.yetland.net.bundle;

import com.yetland.net.bundle.bean.ActivityResp;
import com.yetland.net.bundle.bean.ResponseResp;
import com.yetland.net.bundle.bean.UserResp;

import java.io.IOException;

import javax.inject.Inject;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by yeliang on 2016/6/24.
 */
public class RestApiImpl implements RestApi {

    @Inject
    public void RestApiImpl(){

    }

    private RetrofitApi retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()
            .create(RetrofitApi.class);

    /**
     * 通过Call 实现基础判断
     * @param phoneNumber
     * @param password
     * @return
     */
    @Override
    public Observable<ResponseResp> login(String phoneNumber, String password) {
        // 清空
        Observable.empty().subscribe();
        return Observable.create(new Observable.OnSubscribe<ResponseResp>() {
            @Override
            public void call(Subscriber<? super ResponseResp> subscriber) {
                try {
                    Response<ResponseResp> response = retrofit.login(phoneNumber,password).execute();
                    // 是否访问成功
                    if (response.isSuccessful())
                    {
                        // 获取内容
                        ResponseResp responseResp = response.body();
                        // 判断返回码
                        if (responseResp.code =="" ){
                            // 提取内容
                            subscriber.onNext(responseResp);
                            subscriber.onCompleted();
                        }else {
                            // 报错内容
                        }
                    }
                } catch (IOException e) {
                    // 报错内容
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public Observable<ResponseResp> updateUserInfo(UserResp userResp) {
        Observable.empty().subscribe();

        return Observable.create(new Observable.OnSubscribe<ResponseResp>() {
            @Override
            public void call(Subscriber<? super ResponseResp> subscriber) {
                try {
                    Response<ResponseResp> response = retrofit.updateUserInfo(userResp).execute();
                    if (response.isSuccessful()){
                        ResponseResp responseResp = response.body();
                        if (responseResp.code == "") {
                            subscriber.onNext(responseResp);
                            subscriber.onCompleted();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public Observable<ResponseResp> getMain(int id, String phoneNumber, String password) {
        return null;
    }

    @Override
    public Observable<ResponseResp> createActivity(ActivityResp activityModel) {
        return null;
    }

    @Override
    public Observable<ResponseResp> deleteActivity(ActivityResp activityModel) {
        return null;
    }

    @Override
    public Observable<ResponseResp> getActivityMember(int activityId) {
        return null;
    }

    @Override
    public Observable<ResponseResp> getMyActivity(int memberId) {
        return null;
    }

    @Override
    public Observable<ResponseResp> searchActivity(String title) {
        return null;
    }

    @Override
    public Observable<ResponseResp> getMySchoolMate(int memberId) {
        return null;
    }

    @Override
    public Observable<ResponseResp> searchSchoolMate(String nick) {
        return null;
    }

    @Override
    public Observable<ResponseResp> sendSchoolMateInvitation(int fromId, int toId) {
        return null;
    }

    @Override
    public Observable<ResponseResp> acceptSchoolMateInvitation(int fromId, int toId) {
        return null;
    }

    @Override
    public Observable<ResponseResp> getNotify(int toId) {
        return null;
    }

    @Override
    public Observable<ResponseResp> deleteSchoolMate(int fromId, int toId) {
        return null;
    }

    @Override
    public Observable<ResponseResp> deleteActivityMember(int activityId, int creatorId, int memberId) {
        return null;
    }

    @Override
    public Observable<ResponseResp> joinActivity(int activityId, int creatorId, int memberId) {
        return null;
    }

    @Override
    public Observable<ResponseResp> quitActivity(int activityId, int creatorId, int memberId) {
        return null;
    }

    @Override
    public Observable<ResponseResp> dismissActivity(int activityId, int creatorId) {
        return null;
    }

    @Override
    public Observable<ResponseResp> leaveMessage(int fromId, int toId, String contentMessage) {
        return null;
    }

    @Override
    public Observable<ResponseResp> getMessageBoard(int toId) {
        return null;
    }
}
