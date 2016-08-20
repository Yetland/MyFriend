package com.yetland.net.bundle.bean;

import java.util.List;

/**
 * Created by yeliang on 2016/4/15.
 */
public class ContentResp {

    public UserResp user;// 用户信息的实体类
//    public List<ImageItemResp> imageItemResps;
    public List<ImageItemResp> imageItemResps;
    public List<ActivityResp> activityResps;
    public List<ActivityMemberResp> activityMemberResps;
    public List<SearchUserResp> searchUserModels;
    public List<SchoolMateInvitationResp> schoolMateInvitationResps;
    public List<SchoolMateResp> schoolMateResps;
    public MessageResp messageResp;
}
