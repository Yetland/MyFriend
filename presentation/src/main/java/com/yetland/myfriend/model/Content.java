package com.yetland.myfriend.model;

import java.util.List;

/**
 * Created by yeliang on 2016/4/15.
 */
public class Content {

    private UserModel user;// 用户信息的实体类
    private List<ImageItem> imageItems;
    private List<ActivityModel> activityModels;
    private List<ActivityMember> activityMembers;
    private List<SearchUserModel> searchUserModels;
    private List<SchoolMateInvitation> schoolMateInvitations;
    private List<SchoolMateModel> schoolMateModels;
    private MessageModel messageModel;

    public List<SchoolMateModel> getSchoolMateModels() {
        return schoolMateModels;
    }

    public void setSchoolMateModels(List<SchoolMateModel> schoolMateModels) {
        this.schoolMateModels = schoolMateModels;
    }

    public List<ActivityMember> getActivityMembers() {
        return activityMembers;
    }

    public void setActivityMembers(List<ActivityMember> activityMembers) {
        this.activityMembers = activityMembers;
    }

    public UserModel getUserModel() {
        return user;
    }

    public void setUserModel(UserModel users) {
        this.user = users;
    }

    public List<ImageItem> getImageItems() {
        return imageItems;
    }

    public void setImageItems(List<ImageItem> imageItems) {
        this.imageItems = imageItems;
    }

    public List<ActivityModel> getActivityModels() {
        return activityModels;
    }

    public void setActivityModels(List<ActivityModel> activityModels) {
        this.activityModels = activityModels;
    }

    public List<SearchUserModel> getSearchUserModels() {
        return searchUserModels;
    }

    public void setSearchUserModels(List<SearchUserModel> searchUserModels) {
        this.searchUserModels = searchUserModels;
    }

    public List<SchoolMateInvitation> getSchoolMateInvitations() {
        return schoolMateInvitations;
    }

    public void setSchoolMateInvitations(List<SchoolMateInvitation> schoolMateInvitations) {
        this.schoolMateInvitations = schoolMateInvitations;
    }
    public MessageModel getMessageModel() {
        return messageModel;
    }

    public void setMessageModel(MessageModel messageModel) {
        this.messageModel = messageModel;
    }

    @Override
    public String toString() {
        return "Content [user=" + user + ", imageItems=" + imageItems
                + ", activityModels=" + activityModels + ", activityMembers="
                + activityMembers + ", schoolMateModels=" + schoolMateModels
                + ", searchUserModels=" + searchUserModels
                + ", schoolMateInvitations=" + schoolMateInvitations
                + ", messageModel=" + messageModel + "]";
    }
}
