package com.baeldung.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class UnwrappedUser {
    public int id;

    @JsonUnwrapped
    public Name name;

    public UnwrappedUser() {

    }

    public UnwrappedUser(final int id, final Name name) {
        this.id = id;
        this.name = name;
    }

    public static class Name {
        public String firstName;
        public String lastName;

        public Name() {

        }

        public Name(final String firstName, final String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }
    }

}
