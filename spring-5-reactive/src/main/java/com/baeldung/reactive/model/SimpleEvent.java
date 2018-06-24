package com.baeldung.reactive.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SimpleEvent {
    long id;
    String text;

    public SimpleEvent(@JsonProperty("id") long id, @JsonProperty("text") String text) {
        this.id = id;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "SimpleEvent{id=" + id + ", text=" + text + '}';
    }
}
