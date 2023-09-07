package com.baeldung.constructor;

public class UserService {

    private final String userName;

    public UserService(String userName) {
        this.userName = userName;
    }

    public UserService() {
        this.userName = "Unknown";
    }

    public String getUserName(){
        return this.userName;
    }
}
