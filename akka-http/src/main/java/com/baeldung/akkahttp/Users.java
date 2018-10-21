package com.baeldung.akkahttp;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to send the response enclosed by Users.
 * 
 */
public class Users {

    private final List<User> users;

    public Users() {
        this.users = new ArrayList<>();
    }

    public Users(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }
}
