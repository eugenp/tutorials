package com.baeldung.mongo.crud.model;

import java.time.LocalDateTime;

public class Event {
    public String title;
    public String location;
    public LocalDateTime dateTime;
    public LocalDateTime updatedAt;

    public Event() {}
    public Event(String title, String location, LocalDateTime dateTime) {
        this.title = title;
        this.location = location;
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "\nEvent: " + title + "\nWhere: " + location + "\nWhen: " + dateTime;
    }
}

