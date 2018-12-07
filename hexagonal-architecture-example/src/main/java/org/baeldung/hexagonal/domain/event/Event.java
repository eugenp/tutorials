package org.baeldung.hexagonal.domain.event;

import java.time.LocalDateTime;

public class Event {

    public Event(LocalDateTime dateTimeCreated, String username) {
        this.dateTimeCreated = dateTimeCreated;
        this.username = username;
    }

    private LocalDateTime dateTimeCreated;
    private String username;

    public LocalDateTime getDateTimeCreated() {
        return dateTimeCreated;
    }

    public String getUsername() {
        return username;
    }
}
