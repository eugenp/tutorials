package com.baeldung.tutorial.beaninjection.beans;

public class UserCredentials {

    private String userName;

    public UserCredentials(String uName) {
        setUserName(uName);
    }

    public UserCredentials() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
