package com.baeldung.api;

import com.baeldung.model.User;

public class UserDto {
    public String name;

    protected User toUser() {
        User user = new User();
        user.userName = name;
        return user;
    }
}
