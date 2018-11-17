package com.baeldung.cdi.cdi2observers.events;

public class SimpleEvent {
    
   private final String eventMessage;

    public SimpleEvent(String eventMessage) {
        this.eventMessage = eventMessage;
    }
    
    public String getEventMessage() {
        return eventMessage;
    }
}
