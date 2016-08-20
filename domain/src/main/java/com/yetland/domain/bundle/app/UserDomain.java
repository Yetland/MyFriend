package com.yetland.domain.bundle.app;

/**
 * @author yeliang
 * @data 2016/6/29.
 */
public class UserDomain {

    public int id;
    public String phoneNumber;
    public String password;
    public String registerTime;
    public String email;
    public String userStatus;
    public String nick;
    public String sex;
    public String sign;
    public String schoolName;
    public String graduateYear;
    public String className;


    @Override
    public String toString() {
        return "UserDomain{" +
                "id=" + id +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", registerTime='" + registerTime + '\'' +
                ", email='" + email + '\'' +
                ", userStatus='" + userStatus + '\'' +
                ", nick='" + nick + '\'' +
                ", sex='" + sex + '\'' +
                ", sign='" + sign + '\'' +
                ", schoolName='" + schoolName + '\'' +
                ", graduateYear='" + graduateYear + '\'' +
                ", className='" + className + '\'' +
                '}';
    }
}
