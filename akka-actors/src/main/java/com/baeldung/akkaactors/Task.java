package com.baeldung.akkaactors;

public class Task {
    private final String message;

    public Task(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
