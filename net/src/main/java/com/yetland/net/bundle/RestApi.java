package com.yetland.net.bundle;

import com.yetland.net.bundle.bean.ActivityResp;
import com.yetland.net.bundle.bean.ResponseResp;
import com.yetland.net.bundle.bean.UserResp;

import rx.Observable;

/**
 * Created by yeliang on 2016/6/24.
 */
public interface RestApi {

    String BASE_URL = "http://yetland.xicp.net:29726/";

    Observable<ResponseResp> login(String phoneNumber, String password);

    Observable<ResponseResp> updateUserInfo(UserResp userResp);

    Observable<ResponseResp> getMain(int id, String phoneNumber, String password);

    Observable<ResponseResp> createActivity(ActivityResp activityModel);

    Observable<ResponseResp> deleteActivity(ActivityResp activityModel);

    Observable<ResponseResp> getActivityMember(int activityId);

    Observable<ResponseResp> getMyActivity(int memberId);

    Observable<ResponseResp> searchActivity(String title);

    Observable<ResponseResp> getMySchoolMate(int memberId);

    Observable<ResponseResp> searchSchoolMate(String nick);

    Observable<ResponseResp> sendSchoolMateInvitation(int fromId, int toId);

    Observable<ResponseResp> acceptSchoolMateInvitation(int fromId, int toId);

    Observable<ResponseResp> getNotify(int toId);

    Observable<ResponseResp> deleteSchoolMate(int fromId, int toId);

    Observable<ResponseResp> deleteActivityMember(int activityId, int creatorId, int memberId);

    Observable<ResponseResp> joinActivity(int activityId, int creatorId, int memberId);

    Observable<ResponseResp> quitActivity(int activityId, int creatorId, int memberId);

    Observable<ResponseResp> dismissActivity(int activityId, int creatorId);

    Observable<ResponseResp> leaveMessage(int fromId, int toId, String contentMessage);

    Observable<ResponseResp> getMessageBoard(int toId);

}
