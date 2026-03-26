package com.baeldung.mockingcollections;

import java.util.List;

public class UserService {

    private final List<String> users;

    public UserService(List<String> users) {
        this.users = users;
    }

    public boolean hasUsers() {
        return !users.isEmpty();
    }

    public String getFirstUser() {
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }
}
