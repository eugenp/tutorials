package com.baeldung.hexagonalarchitecture.userside.request;

import com.baeldung.hexagonalarchitecture.businesslogic.dto.User;

import java.util.UUID;

public class UserCreateRequest {
    private String name;
    private Integer age;

    public UserCreateRequest() {
    }

    public UserCreateRequest(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public User getUser() {
        User user = new User(UUID.randomUUID(), this.getName(), this.getAge(), false);
        return user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
