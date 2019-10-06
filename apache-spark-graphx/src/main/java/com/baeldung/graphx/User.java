package com.baeldung.graphx;

import java.io.Serializable;

public class User implements Serializable {

    private Long id;
    private String name;

    public User(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "(" + id + "-" + name + ")";
    }
}
