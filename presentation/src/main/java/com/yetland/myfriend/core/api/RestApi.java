package com.yetland.myfriend.core.api;

import com.yetland.myfriend.model.ActivityModel;
import com.yetland.myfriend.model.ResponseModel;
import com.yetland.myfriend.model.UserModel;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by yeliang on 2016/4/15.
 */
public interface RestApi {

            String BASE_URL = "http://yetland.xicp.net:29726/";
//    String BASE_URL = "http://172.30.16.227:8080/";
//        String BASE_URL = "http://192.168.56.1:8080/";
    String PROJECT_NAME = "TeamClock";

    @FormUrlEncoded
    @POST(PROJECT_NAME + "/login?")
    Observable<ResponseModel> login(@Field("pn") String phoneNumber, @Field("pw") String password);

    @POST(PROJECT_NAME + "/updateUserInfo?")
    Observable<ResponseModel> updateUserInfo(@Body UserModel userModel);

    @FormUrlEncoded
    @POST(PROJECT_NAME + "/getMain?")
    Observable<ResponseModel> getMain(@Field("id") int id, @Field("pn") String phoneNumber, @Field("pw") String password);

    @POST(PROJECT_NAME + "/createActivity?")
    Observable<ResponseModel> createActivity(@Body ActivityModel activityModel);

    @POST(PROJECT_NAME + "/deleteActivity?")
    Observable<ResponseModel> deleteActivity(@Body ActivityModel activityModel);

    @FormUrlEncoded
    @POST(PROJECT_NAME + "/getActivityMember?")
    Observable<ResponseModel> getActivityMember(@Field("activityId") int activityId);

    @FormUrlEncoded
    @POST(PROJECT_NAME + "/getMyActivity?")
    Observable<ResponseModel> getMyActivity(@Field("memberId") int memberId);

    @FormUrlEncoded
    @POST(PROJECT_NAME + "/searchActivity?")
    Observable<ResponseModel> searchActivity(@Field("title") String title);

    @FormUrlEncoded
    @POST(PROJECT_NAME + "/getMySchoolMate?")
    Observable<ResponseModel> getMySchoolMate(@Field("fromId") int memberId);

    @FormUrlEncoded
    @POST(PROJECT_NAME + "/searchSchoolMate?")
    Observable<ResponseModel> searchSchoolMate(@Field("nick") String nick);

    @FormUrlEncoded
    @POST(PROJECT_NAME + "/sendSchoolMateInvitation?")
    Observable<ResponseModel> sendSchoolMateInvitation(@Field("fromId") int fromId, @Field("toId") int toId);

    @FormUrlEncoded
    @POST(PROJECT_NAME + "/acceptSchoolMateInvitation?")
    Observable<ResponseModel> acceptSchoolMateInvitation(@Field("fromId") int fromId, @Field("toId") int toId);

    @FormUrlEncoded
    @POST(PROJECT_NAME + "/getNotify?")
    Observable<ResponseModel> getNotify(@Field("toId") int toId);

    @FormUrlEncoded
    @POST(PROJECT_NAME + "/deleteSchoolMate?")
    Observable<ResponseModel> deleteSchoolMate(@Field("fromId") int fromId, @Field("toId") int toId);

    @FormUrlEncoded
    @POST(PROJECT_NAME + "/deleteActivityMember?")
    Observable<ResponseModel> deleteActivityMember(@Field("activityId") int activityId, @Field("creatorId") int creatorId, @Field("memberId") int memberId);

    @FormUrlEncoded
    @POST(PROJECT_NAME + "/joinActivity?")
    Observable<ResponseModel> joinActivity(@Field("activityId") int activityId, @Field("creatorId") int creatorId, @Field("memberId") int memberId);

    @FormUrlEncoded
    @POST(PROJECT_NAME + "/quitActivity?")
    Observable<ResponseModel> quitActivity(@Field("activityId") int activityId, @Field("creatorId") int creatorId, @Field("memberId") int memberId);

    @FormUrlEncoded
    @POST(PROJECT_NAME + "/dismissActivity?")
    Observable<ResponseModel> dismissActivity(@Field("activityId") int activityId, @Field("creatorId") int creatorId);

    @FormUrlEncoded
    @POST(PROJECT_NAME + "/leaveMessage?")
    Observable<ResponseModel> leaveMessage(@Field("fromId") int fromId, @Field("toId") int toId, @Field("contentMessage") String contentMessage);

    @FormUrlEncoded
    @POST(PROJECT_NAME + "/getMessageBoard?")
    Observable<ResponseModel> getMessageBoard(@Field("toId") int toId);

}
