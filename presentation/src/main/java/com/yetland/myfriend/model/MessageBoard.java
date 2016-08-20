package com.yetland.myfriend.model;

import java.io.Serializable;

/**
 * Created by yeliang on 2016/4/27.
 */
public class MessageBoard implements Serializable{
    private int messageBoardId;
    private int fromId;
    private int toId;
    private String contentMessage;
    private String time;

    public int getMessageBoardId() {
        return messageBoardId;
    }

    public void setMessageBoardId(int messageBoardId) {
        this.messageBoardId = messageBoardId;
    }

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

    public String getContentMessage() {
        return contentMessage;
    }

    public void setContentMessage(String contentMessage) {
        this.contentMessage = contentMessage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "MessageBoard{" +
                "messageBoardId=" + messageBoardId +
                ", fromId=" + fromId +
                ", toId=" + toId +
                ", contentMessage='" + contentMessage + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
