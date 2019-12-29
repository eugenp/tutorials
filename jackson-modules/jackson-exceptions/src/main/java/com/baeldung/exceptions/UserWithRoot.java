package com.baeldung.exceptions;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "user")
public class UserWithRoot {
    public int id;
    public String name;

    public UserWithRoot() {
        super();
    }

    public UserWithRoot(final int id, final String name) {
        this.id = id;
        this.name = name;
    }
}
