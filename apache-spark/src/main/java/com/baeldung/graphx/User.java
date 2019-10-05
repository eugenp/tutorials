package com.baeldung.graphx;

public class User {

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
