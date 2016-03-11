package com.baeldung.jackson.exception;

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
