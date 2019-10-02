package org.baeldung.javabeanconstraints.entities;

import javax.validation.constraints.NotNull;

public class UserNotNull {

    @NotNull(message = "Name is mandatory")
    private final String name;

    public UserNotNull(String name) {
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
