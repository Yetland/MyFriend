package com.yetland.myfriend.model;

import java.io.Serializable;

/**
 * Created by yeliang on 2016/4/20.
 */
public class SchoolMateInvitation implements Serializable {
    private int fromId;
    private int toId;
    private String nick;
    private String schoolName;
    private String graduateYear;
    private String className;
    private String time;
    private String status;

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getGraduateYear() {
        return graduateYear;
    }

    public void setGraduateYear(String graduateYear) {
        this.graduateYear = graduateYear;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SchoolMateInvitation{" +
                "fromId=" + fromId +
                ", toId=" + toId +
                ", nick='" + nick + '\'' +
                ", schoolName='" + schoolName + '\'' +
                ", graduateYear='" + graduateYear + '\'' +
                ", className='" + className + '\'' +
                ", time='" + time + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}


