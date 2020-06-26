package com.baeldung.entity;

public class User {

    private final String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "User{" + "name=" + name + '}';
    }
}
