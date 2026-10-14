package com.baeldung.invaliddefinitionexception;

public class Notification {

    private final String message;
    private final int priority;

    public Notification(String message, int priority) {
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
