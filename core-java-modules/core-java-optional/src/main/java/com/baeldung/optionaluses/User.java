package com.baeldung.optionaluses;

public class User {

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    private String id;

    private String name;

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
