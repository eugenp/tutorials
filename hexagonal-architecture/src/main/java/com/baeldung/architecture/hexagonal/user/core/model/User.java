package com.baeldung.architecture.hexagonal.user.core.model;

public class User {

    private final String id;

    private final String name;

    private final String surname;

    public User(String id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
