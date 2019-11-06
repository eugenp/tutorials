package com.baeldung.architecture.hexagonal.personal.library.core.domain;

import java.io.Serializable;

public class Author implements Serializable {

    private final String firstName;
    private final String lastName;

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
