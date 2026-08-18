package com.baeldung.invaliddefinitionexception;

public class NotificationWithDefaultConstructor {

    private String message;
    private int priority;

    public NotificationWithDefaultConstructor() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}