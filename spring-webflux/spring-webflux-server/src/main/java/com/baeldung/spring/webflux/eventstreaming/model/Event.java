package com.baeldung.spring.webflux.eventstreaming.model;

import java.time.LocalDateTime;

public class Event {

    private long id;

    private String message;

    private LocalDateTime date;

    // No-arg constructor is mandatory 
    public Event() {
        
    }
    public Event(long id, String message, LocalDateTime date) {
        super();
        this.id = id;
        this.message = message;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Event [id=" + id + ", message=" + message + ", date=" + date + "]";
    }

}
