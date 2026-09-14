package com.baeldung.invaliddefinitionexception;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NotificationWithCreator {

    private final String message;
    private final int priority;

    @JsonCreator
    public NotificationWithCreator(@JsonProperty("message") String message, @JsonProperty("priority") int priority) {
        this.message = message;
        this.priority = priority;
    }

    public String getMessage() {
        return message;
    }

    public int getPriority() {
        return priority;
    }
}
