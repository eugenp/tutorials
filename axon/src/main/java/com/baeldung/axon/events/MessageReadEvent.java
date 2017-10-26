package com.baeldung.axon.events;

public class MessageReadEvent {
 
    private final String id;
 
    public MessageReadEvent(String id) {
        this.id = id;
    }
 
    public String getId() {
        return id;
    }
}