package com.baeldung.guava.entity;

public class Profile {
    private long id;
    private String type;

    public Profile(long id, String type) {
        this.id = id;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return type;
    }

}
