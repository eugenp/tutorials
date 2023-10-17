package com.baeldung.kafka.message.ordering.payload;

import java.util.Objects;
public class UserEvent implements Comparable<UserEvent> {
    private String userEventId;

    private long eventNanoTime;

    private long globalSequenceNumber;

    public UserEvent(){

    }

    //Required for Kafka Serialization and Deserialization
    public UserEvent(String userEventId) {
        this.userEventId = userEventId;
    }

    public String getUserEventId() {
        return userEventId;
    }

    public long getEventNanoTime() {
        return eventNanoTime;
    }

    public void setEventNanoTime(long eventNanoTime) {
        this.eventNanoTime = eventNanoTime;
    }

    public long getGlobalSequenceNumber() {
        return globalSequenceNumber;
    }

    public void setGlobalSequenceNumber(long globalSequenceNumber) {
        this.globalSequenceNumber = globalSequenceNumber;
    }

    @Override
    public int compareTo(UserEvent other) {
        return Long.compare(this.globalSequenceNumber, other.globalSequenceNumber);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof UserEvent)) {
            return false;
        }
        UserEvent userEvent = (UserEvent) obj;
        return Objects.equals(this.userEventId, userEvent.getUserEventId())
                && userEvent.getEventNanoTime() == this.eventNanoTime;
    }
}

