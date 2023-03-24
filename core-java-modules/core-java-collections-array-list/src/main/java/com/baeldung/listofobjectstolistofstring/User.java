package com.baeldung.listofobjectstolistofstring;

public class User {
    private final String fullName;

    public User(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "User (" + "full name='" + fullName + ')';
    }
}