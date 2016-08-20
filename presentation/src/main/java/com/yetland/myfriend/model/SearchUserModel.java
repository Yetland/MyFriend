package com.yetland.myfriend.model;

import java.io.Serializable;

/**
 * Created by yeliang on 2016/4/25.
 */
public class SearchUserModel implements Serializable{
    private int id;
    private String phoneNumber;
    private String email;
    private String status;
    private String nick;
    private String sex;
    private String sign;
    private String schoolName;
    private String graduateYear;
    private String className;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getNick() {
        return nick;
    }
    public void setNick(String nick) {
        this.nick = nick;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getSign() {
        return sign;
    }
    public void setSign(String sign) {
        this.sign = sign;
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
    @Override
    public String toString() {
        return "SearchUser [id=" + id + ", phoneNumber=" + phoneNumber
                + ", email=" + email + ", status=" + status + ", nick=" + nick
                + ", sex=" + sex + ", sign=" + sign + ", schoolName="
                + schoolName + ", graduateYear=" + graduateYear
                + ", className=" + className + "]";
    }
}
