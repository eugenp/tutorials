package com.baeldung.jupiter;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Task {

    private final String name;

    private final int id;

    public Task(@JsonProperty("name") String name, @JsonProperty("id") int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Task{" + "name='" + name + '\'' + ", id=" + id + '}';
    }
}
