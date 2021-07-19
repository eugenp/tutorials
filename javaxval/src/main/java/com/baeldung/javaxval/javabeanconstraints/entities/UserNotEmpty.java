package com.baeldung.javaxval.javabeanconstraints.entities;

import javax.validation.constraints.NotEmpty;

public class UserNotEmpty {

    @NotEmpty(message = "Name is mandatory")
    private final String name;

    public UserNotEmpty(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "User{" + "name=" + name + "}";
    }
}
