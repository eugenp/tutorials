package com.baeldung.jackson.date;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class EventWithLocalDateTime {
    private String name;

    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime eventDate;

    public EventWithLocalDateTime() {
        super();
    }

    public EventWithLocalDateTime(final String name, final LocalDateTime eventDate) {
        this.name = name;
        this.eventDate = eventDate;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public String getName() {
        return name;
    }
}
