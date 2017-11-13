package com.baeldung.ditypes;

import org.springframework.stereotype.Component;

@Component
public class User {

    private String name;
    private int id;

    public User(String name, int id) {
        this.id = id;
        this.name = name;
    }

    public String register() {
        return "User" + id + ": " + name + " is registered";
    }

}
