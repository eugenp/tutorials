package com.baeldung.webfluxdemo.model;

public class Event {

    private Integer id;
    private String message;

    public Event(Integer id, String message) {
        this.id = id;
        this.message = message;
    }

    public Event() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
