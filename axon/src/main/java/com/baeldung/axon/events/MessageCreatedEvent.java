package com.baeldung.axon.events;

public class MessageCreatedEvent {
 
    private final String id;
    private final String text;
 
    public MessageCreatedEvent(String id, String text) {
        this.id = id;
        this.text = text;
    }
 
    public String getId() {
        return id;
    }
 
    public String getText() {
        return text;
    }
}