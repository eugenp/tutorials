package com.baeldung.guava.eventbus;

public class CustomEvent {
    private String action;

    CustomEvent(String action) {
        this.action = action;
    }

    String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
