package com.baeldung.bean;

import com.baeldung.repo.UserRepository;

public class UserService {

    private String name;
    private int value;
    private String lastName;
    private UserRepository userRepository;

    public UserService() {
    }

    public UserService(String name) {
        this.name = name;
    }

    public UserService(int value) {
        this.value = value;
    }

    public UserService(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //getters
    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public String getLastName() {
        return lastName;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    //setters

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
