package com.baeldung.streams.firstmatchingelement;

public class User {

    private String userName;
    private Integer userId;

    public User(Integer userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public Integer getUserId() {
        return userId;
    }

}
