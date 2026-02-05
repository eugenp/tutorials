package com.baeldung.apachefury.event;

import java.io.Serializable;
import java.util.Objects;

public class UserEvent implements Serializable {
    private final String userId;
    private final String eventType;
    private final long timestamp;
    private final Address address;

    public UserEvent(String userId, String eventType, long timestamp, Address address) {
        this.userId = userId;
        this.eventType = eventType;
        this.timestamp = timestamp;
        this.address = address;
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

    public Address getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEvent)) return false;
        UserEvent userEvent = (UserEvent) o;
        return timestamp == userEvent.timestamp && userId.equals(userEvent.userId) && eventType.equals(userEvent.eventType) && address.equals(userEvent.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, eventType, timestamp, address);
    }
}