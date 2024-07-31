package com.baeldung.dtopattern.domain;

import java.util.Objects;

public class Role {
    private String id;
    private String name;

    public Role(String name) {
        this.name = Objects.requireNonNull(name);
    }

    public String getId() {
        return id;
    }

    void setId(String id) {
        this.id = Objects.requireNonNull(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name);
    }
}
