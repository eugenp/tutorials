package com.baeldung.javahexagonal.domain;

public class Event {

    private final String id;

    private final String name;

    public Event(String id, String name) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        if (name == null) {
            throw new IllegalArgumentException("name must not be null");
        }

        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
