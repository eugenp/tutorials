package com.baeldung.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonIgnoreType;

public class UserWithIgnoreType {
    public int id;

    public Name name;

    public UserWithIgnoreType() {

    }

    public UserWithIgnoreType(final int id, final Name name) {
        this.id = id;
        this.name = name;
    }

    @JsonIgnoreType
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
