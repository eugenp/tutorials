package com.baeldung.mongo.crud.model;

import java.time.LocalDateTime;

public class Event {
    public String title;
    public String location;
    public LocalDateTime dateTime;
    public String timeZoneOffset;

    public Event() {}
    public Event(String title, String location, LocalDateTime dateTime) {
        this.title = title;
        this.location = location;
        this.dateTime = dateTime;
    }

    public Event(String title, String location, LocalDateTime dateTime, String timeZoneOffset) {
        this.title = title;
        this.location = location;
        this.dateTime = dateTime;
        this.timeZoneOffset = timeZoneOffset;
    }

    @Override
    public String toString() { return "\nEvent: " + title + "\nWhere: " + location + "\nWhen: " + dateTime; }
}

