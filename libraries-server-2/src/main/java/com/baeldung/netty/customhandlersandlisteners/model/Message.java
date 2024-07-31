package com.baeldung.netty.customhandlersandlisteners.model;

import java.time.Instant;

public class Message {

    private final Instant time;
    private final String user;
    private final String message;

    public Message(String user, String message) {
        this.time = Instant.now();
        this.user = user;
        this.message = message;
    }

    public Instant getTime() {
        return time;
    }

    public String getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return time + " - " + user + ": " + message;
    }

    public static Message parse(String string) {
        String[] arr = string.split(";", 2);
        return new Message(arr[0], arr[1]);
    }
}
