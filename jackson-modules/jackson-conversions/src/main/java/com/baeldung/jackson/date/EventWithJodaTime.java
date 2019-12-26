package com.baeldung.jackson.date;

import org.joda.time.DateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class EventWithJodaTime {
    public String name;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    public DateTime eventDate;

    public EventWithJodaTime() {
        super();
    }

    public EventWithJodaTime(final String name, final DateTime eventDate) {
        this.name = name;
        this.eventDate = eventDate;
    }

    public DateTime getEventDate() {
        return eventDate;
    }

    public String getName() {
        return name;
    }
}
