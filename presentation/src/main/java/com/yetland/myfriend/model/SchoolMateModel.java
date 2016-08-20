package com.yetland.myfriend.model;

import java.io.Serializable;

public class SchoolMateModel implements Serializable{

	private int toId;
	private String phoneNumber;
	private String email;
	private String nick;
	private String sex;
	private String sign;
	private String schoolName;
	private String graduateYear;
	private String className;
	private String createTime;

	public int getToId() {
		return toId;
	}

	public void setToId(int toId) {
		this.toId = toId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "SchoolMateModel{" +
				"toId=" + toId +
				", phoneNumber='" + phoneNumber + '\'' +
				", email='" + email + '\'' +
				", nick='" + nick + '\'' +
				", sex='" + sex + '\'' +
				", sign='" + sign + '\'' +
				", schoolName='" + schoolName + '\'' +
				", graduateYear='" + graduateYear + '\'' +
				", className='" + className + '\'' +
				", createTime='" + createTime + '\'' +
				'}';
	}
}
