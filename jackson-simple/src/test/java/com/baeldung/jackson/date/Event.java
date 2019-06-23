package com.baeldung.jackson.date;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Event {
    public String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    public Date eventDate;

    public Event() {
        super();
    }

    public Event(final String name, final Date eventDate) {
        this.name = name;
        this.eventDate = eventDate;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public String getName() {
        return name;
    }
}
