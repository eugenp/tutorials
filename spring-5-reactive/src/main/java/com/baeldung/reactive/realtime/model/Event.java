package com.baeldung.reactive.realtime.model;

import java.io.Serializable;

public class Event implements Serializable {

    private long id;

    public Event() {
    }

    public Event(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
