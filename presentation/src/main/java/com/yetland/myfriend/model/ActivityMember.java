package com.yetland.myfriend.model;

import java.io.Serializable;

public class ActivityMember implements Serializable{

	private static final long serialVersionUID = 1L;

	private int activityId;
	private int creatorId;
	private int memberId;
	private String nick;
	private String email;
	private String age;
	private String sex;
	private String sign;
	private String schoolName;
	private String graduateYear;
	private String className;

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

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
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

	@Override
	public String toString() {
		return "ActivityMember{" +
				"activityId=" + activityId +
				", creatorId=" + creatorId +
				", memberId=" + memberId +
				", nick='" + nick + '\'' +
				", email='" + email + '\'' +
				", age='" + age + '\'' +
				", sex='" + sex + '\'' +
				", sign='" + sign + '\'' +
				", schoolName='" + schoolName + '\'' +
				", graduateYear='" + graduateYear + '\'' +
				", className='" + className + '\'' +
				'}';
	}
}
