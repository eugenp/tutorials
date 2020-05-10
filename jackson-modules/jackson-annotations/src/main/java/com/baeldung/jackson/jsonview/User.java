package com.baeldung.jackson.jsonview;

import com.fasterxml.jackson.annotation.JsonView;

public class User {
    public int id;

    @JsonView(Views.Public.class)
    public String name;

    public User() {
        super();
    }

    public User(final int id, final String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
