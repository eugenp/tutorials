package com.baeldung.apachefury.event;

import java.io.Serializable;
import java.util.Objects;

public class UserEvent implements Serializable {
    private final String userId;
    private final String eventType;
    private final long timestamp;

    public UserEvent(String userId, String eventType, long timestamp) {
        this.userId = userId;
        this.eventType = eventType;
        this.timestamp = timestamp;
    }

    // Getters and setters
    public String getUserId() {
        return userId;
    }

    public String getEventType() {
        return eventType;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEvent)) return false;
        UserEvent userEvent = (UserEvent) o;
        return timestamp == userEvent.timestamp && Objects.equals(userId, userEvent.userId) && Objects.equals(eventType, userEvent.eventType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, eventType, timestamp);
    }
}