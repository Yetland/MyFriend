package com.yetland.myfriend.model;

import java.io.Serializable;

public class GroupModel implements Serializable{

	private static final long serialVersionUID = 1L;

	public static final String GROUP_ID = "groupId";
	public static final String CREATOR_ID = "creatorId";
	public static final String CREATOR_NAME = "creatorName";
	public static final String GROUP_NAME = "groupName";
	public static final String TIME = "time";
	public static final String STATUS= "status";
	private int groupId;
	private int creatorId;
	private String creatorName;
	private String groupName;
	private String time;
	private String status;
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public int getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
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
		return "GroupModel [groupId=" + groupId + ", creatorId=" + creatorId
				+ ", creatorName=" + creatorName + ", groupName=" + groupName
				+ ", time=" + time + ", status=" + status + "]";
	}
}
