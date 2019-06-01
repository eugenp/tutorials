package com.example.hexagonalarch.mapper;

import com.example.hexagonalarch.api.request.UserCreateRq;
import com.example.hexagonalarch.vo.User;

public class UserCreateRqMapper {

    private UserCreateRqMapper() {

    }

    public static User mapToUser(UserCreateRq userCreateRq) {
        User user = new User();
        user.setName(userCreateRq.getName());
        user.setAge(userCreateRq.getAge());
        return user;
    }
}
