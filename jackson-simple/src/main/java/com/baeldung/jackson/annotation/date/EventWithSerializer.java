package com.baeldung.jackson.annotation.date;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class EventWithSerializer {
    public String name;

    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonSerialize(using = CustomDateSerializer.class)
    public Date eventDate;

    public EventWithSerializer() {
        super();
    }

    public EventWithSerializer(final String name, final Date eventDate) {
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
