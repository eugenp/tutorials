package com.baeldung.jackson.date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;

public class EventWithLocalDate {
    public String name;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    public LocalDate eventDate;

    public EventWithLocalDate() {}

    public EventWithLocalDate(final String name, final LocalDate eventDate) {
        this.name = name;
        this.eventDate = eventDate;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public String getName() {
        return name;
    }
}
