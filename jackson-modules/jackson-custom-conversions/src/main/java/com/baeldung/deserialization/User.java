package com.baeldung.deserialization;

public class User {
    public int id;
    public String name;

    public User() {
        super();
    }

    public User(final int id, final String name) {
        this.id = id;
        this.name = name;
    }

    // API

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}