package com.yetland.myfriend.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yeliang on 2016/4/27.
 */
public class MessageModel implements Serializable{

    private List<UserModel> fromUsers;
    private UserModel toUser;
    private List<MessageBoard> messageBoard;

    public List<UserModel> getFromUsers() {
        return fromUsers;
    }

    public void setFromUsers(List<UserModel> fromUsers) {
        this.fromUsers = fromUsers;
    }

    public UserModel getToUser() {
        return toUser;
    }

    public void setToUser(UserModel toUser) {
        this.toUser = toUser;
    }

    public List<MessageBoard> getMessageBoard() {
        return messageBoard;
    }

    public void setMessageBoard(List<MessageBoard> messageBoard) {
        this.messageBoard = messageBoard;
    }

    @Override
    public String toString() {
        return "MessageModel{" +
                "fromUsers=" + fromUsers +
                ", toUser=" + toUser +
                ", messageBoard=" + messageBoard +
                '}';
    }
}
