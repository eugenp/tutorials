package com.baeldung.constructor;

public class User {

    private final UserService userService;

    public User() {
        this.userService = new UserService();
    }

    public User(String userName) {
        this.userService = new UserService(userName);
    }

    public String getUserName(){
        return userService.getUserName();
    }
}
