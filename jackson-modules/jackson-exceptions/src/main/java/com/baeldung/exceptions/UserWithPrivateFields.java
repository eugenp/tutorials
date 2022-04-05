package com.baeldung.exceptions;

public class UserWithPrivateFields {
    int id;
    String name;

    public UserWithPrivateFields() {
        super();
    }

    public UserWithPrivateFields(final int id, final String name) {
        this.id = id;
        this.name = name;
    }

}
