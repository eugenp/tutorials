package com.baeldung.nullobject;

public class Message {

    private String body;

    private String priority;

    public Message(String body, String priority) {
        this.body = body;
        this.priority = priority;
    }

    public String getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return "{body='" + body + '\'' +
                ", priority='" + priority + '\'' +
                '}';
    }
}
