package com.baeldung.hexagonal.domain;

public class ChatUser {
    private String name;

    public ChatUser(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
