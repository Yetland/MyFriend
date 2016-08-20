package com.yetland.myfriend.model;


import java.io.Serializable;

public class UserModel implements Serializable{

	public static final String USER_ID = "id";
	public static final String PHONE_NUMBER= "phoneNumber";
	public static final String PASSWORD = "password";
	public static final String REGISTER_TIME = "registerTime";
	public static final String EMAIL= "email";
	public static final String STATUS = "status";
	public static final String NICK = "nick";
	public static final String SEX= "sex";
	public static final String SIGN= "sign";
	public static final String SCHOOL_NAME = "schoolName";
	public static final String GRADUATE_YEAR= "graduateYear";
	public static final String CLASS_NAME= "className";

	private int id;
	private String phoneNumber;
	private String password;
	private String registerTime;
	private String email;
	private String userStatus;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}
	public String getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
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
		return "UserModel{" +
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