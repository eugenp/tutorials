package com.baeldung.spring.kafka.start.stop.consumer;

public class UserEvent {
    private String userEventId;
    private long eventNanoTime;

    public UserEvent(){}

    public UserEvent(String userEventId) {
        this.userEventId = userEventId;
    }

    public String getUserEventId() {
        return userEventId;
    }

    public void setUserEventId(String userEventId) {
        this.userEventId = userEventId;
    }

    public long getEventNanoTime() {
        return eventNanoTime;
    }

    public void setEventNanoTime(long eventNanoTime) {
        this.eventNanoTime = eventNanoTime;
    }
}
