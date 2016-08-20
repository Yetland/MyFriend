package com.yetland.myfriend.model;

import java.io.Serializable;

public class ActivityModel implements Serializable {

    private int activityId;// 活动Id
    private int creatorId;// 创建者Id
    private String phoneNumber;// 创建者phoneNumber
    private String nick;// 创建者名字包括班级名+姓名
    private String graduateYear;// 毕业年份
    private String className;// 班级名
    private String schoolName;// 学校名
    private String title;// 活动的标题
    private String contentMessage;// 活动内容简介
    private String createTime;// 活动创建时间

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getGraduateYear() {
        return graduateYear;
    }

    public void setGraduateYear(String graduateYear) {
        this.graduateYear = graduateYear;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentMessage() {
        return contentMessage;
    }

    public void setContentMessage(String contentMessage) {
        this.contentMessage = contentMessage;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    @Override
    public String toString() {
        return "ActivityModel{" +
                "activityId='" + activityId + '\'' +
                ", creatorId='" + creatorId + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", nick='" + nick + '\'' +
                ", graduateYear='" + graduateYear + '\'' +
                ", className='" + className + '\'' +
                ", schoolName='" + schoolName + '\'' +
                ", title='" + title + '\'' +
                ", contentMessage='" + contentMessage + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}