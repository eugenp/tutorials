package com.baeldung.spring.kafka.startstopconsumer;

public class UserEvent {
    private String userEventId;

    public UserEvent() {
    }

    public UserEvent(String userEventId) {
        this.userEventId = userEventId;
    }

    public String getUserEventId() {
        return userEventId;
    }

    public void setUserEventId(String userEventId) {
        this.userEventId = userEventId;
    }
}
