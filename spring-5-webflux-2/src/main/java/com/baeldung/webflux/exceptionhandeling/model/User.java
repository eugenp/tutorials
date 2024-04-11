package com.baeldung.webflux.exceptionhandeling.model;

public class User {
    private String id;
    private String username;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User(String userId, String userName) {
        this.id = userId;
        this.username = userName;

    }
}

