package com.example.mywechat.entity;

public class Message {
    private int id;
    private String fromUser;
    private String toUser;
    private String msg;
    private String time;
    private String toUserAvatar;

    public Message() {
    }

    // 构造函数
    public Message(int id, String fromUser, String toUser, String msg, String time, String toUserAvatar) {
        this.id = id;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.msg = msg;
        this.time = time;
        this.toUserAvatar = toUserAvatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getToUserAvatar() {
        return toUserAvatar;
    }

    public void setToUserAvatar(String toUserAvatar) {
        this.toUserAvatar = toUserAvatar;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", fromUser='" + fromUser + '\'' +
                ", toUser='" + toUser + '\'' +
                ", msg='" + msg + '\'' +
                ", time='" + time + '\'' +
                ", toUserAvatar='" + toUserAvatar + '\'' +
                '}';
    }
}
