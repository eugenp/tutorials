package com.baeldung.model;

import java.util.Date;

public class Event {

    private long id;

    private Date createdAt = new Date();

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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
