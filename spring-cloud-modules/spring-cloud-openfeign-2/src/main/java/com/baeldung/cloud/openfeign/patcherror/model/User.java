package com.baeldung.cloud.openfeign.patcherror.model;

public class User {

    private String userId;

    private String userName;

    private String email;

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }
}
