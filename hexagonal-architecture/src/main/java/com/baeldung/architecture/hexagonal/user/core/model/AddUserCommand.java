package com.baeldung.architecture.hexagonal.user.core.model;

public class AddUserCommand {

    private final String name;

    private final String surname;

    public AddUserCommand(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
