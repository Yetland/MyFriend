package com.yetland.net.bundle;

import com.yetland.net.bundle.bean.ActivityResp;
import com.yetland.net.bundle.bean.ResponseResp;
import com.yetland.net.bundle.bean.UserResp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by yeliang on 2016/6/24.
 */
public interface RetrofitApi {

    String PROJECT_NAME = "TeamClock";

    @FormUrlEncoded
    @POST(PROJECT_NAME + "/login?")
    Call<ResponseResp> login(@Field("pn") String phoneNumber, @Field("pw") String password);

    @POST(PROJECT_NAME + "/updateUserInfo?")
    Call<ResponseResp> updateUserInfo(@Body UserResp userResp);

    @FormUrlEncoded
    @POST(PROJECT_NAME + "/getMain?")
    Call<ResponseResp> getMain(@Field("id") int id, @Field("pn") String phoneNumber, @Field("pw") String password);

    @POST(PROJECT_NAME + "/createActivity?")
    Call<ResponseResp> createActivity(@Body ActivityResp activityModel);

    @POST(PROJECT_NAME + "/deleteActivity?")
    Call<ResponseResp> deleteActivity(@Body ActivityResp activityModel);

    @FormUrlEncoded
    @POST(PROJECT_NAME + "/getActivityMember?")
    Call<ResponseResp> getActivityMember(@Field("activityId") int activityId);

    @FormUrlEncoded
    @POST(PROJECT_NAME + "/getMyActivity?")
    Call<ResponseResp> getMyActivity(@Field("memberId") int memberId);

    @FormUrlEncoded
    @POST(PROJECT_NAME + "/searchActivity?")
    Call<ResponseResp> searchActivity(@Field("title") String title);

    @FormUrlEncoded
    @POST(PROJECT_NAME + "/getMySchoolMate?")
    Call<ResponseResp> getMySchoolMate(@Field("fromId") int memberId);

    @FormUrlEncoded
    @POST(PROJECT_NAME + "/searchSchoolMate?")
    Call<ResponseResp> searchSchoolMate(@Field("nick") String nick);

    @FormUrlEncoded
    @POST(PROJECT_NAME + "/sendSchoolMateInvitation?")
    Call<ResponseResp> sendSchoolMateInvitation(@Field("fromId") int fromId, @Field("toId") int toId);

    @FormUrlEncoded
    @POST(PROJECT_NAME + "/acceptSchoolMateInvitation?")
    Call<ResponseResp> acceptSchoolMateInvitation(@Field("fromId") int fromId, @Field("toId") int toId);

    @FormUrlEncoded
    @POST(PROJECT_NAME + "/getNotify?")
    Call<ResponseResp> getNotify(@Field("toId") int toId);

    @FormUrlEncoded
    @POST(PROJECT_NAME + "/deleteSchoolMate?")
    Call<ResponseResp> deleteSchoolMate(@Field("fromId") int fromId, @Field("toId") int toId);

    @FormUrlEncoded
    @POST(PROJECT_NAME + "/deleteActivityMember?")
    Call<ResponseResp> deleteActivityMember(@Field("activityId") int activityId, @Field("creatorId") int creatorId, @Field("memberId") int memberId);

    @FormUrlEncoded
    @POST(PROJECT_NAME + "/joinActivity?")
    Call<ResponseResp> joinActivity(@Field("activityId") int activityId, @Field("creatorId") int creatorId, @Field("memberId") int memberId);

    @FormUrlEncoded
    @POST(PROJECT_NAME + "/quitActivity?")
    Call<ResponseResp> quitActivity(@Field("activityId") int activityId, @Field("creatorId") int creatorId, @Field("memberId") int memberId);

    @FormUrlEncoded
    @POST(PROJECT_NAME + "/dismissActivity?")
    Call<ResponseResp> dismissActivity(@Field("activityId") int activityId, @Field("creatorId") int creatorId);

    @FormUrlEncoded
    @POST(PROJECT_NAME + "/leaveMessage?")
    Call<ResponseResp> leaveMessage(@Field("fromId") int fromId, @Field("toId") int toId, @Field("contentMessage") String contentMessage);

    @FormUrlEncoded
    @POST(PROJECT_NAME + "/getMessageBoard?")
    Call<ResponseResp> getMessageBoard(@Field("toId") int toId);
}
