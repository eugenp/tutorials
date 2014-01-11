package org.baeldung.jackson.dtos;

public class User {
    public final int id;
    public final String name;

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