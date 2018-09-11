package com.baeldung.eventstreaming.model;
import java.io.Serializable;

public class Event implements Serializable {

    private String message;

    public Event() {
    }

    public Event(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}