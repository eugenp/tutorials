package com.baeldung.invaliddefinitionexception;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NotificationWithFactory {

    private final String message;
    private final int priority;

    private NotificationWithFactory(String message, int priority) {
        this.message = message;
        this.priority = priority;
    }

    @JsonCreator
    public static NotificationWithFactory create(
        @JsonProperty("message") String message,
        @JsonProperty("priority") int priority) {
        return new NotificationWithFactory(message, priority);
    }

    public String getMessage() {
        return message;
    }

    public int getPriority() {
        return priority;
    }
}