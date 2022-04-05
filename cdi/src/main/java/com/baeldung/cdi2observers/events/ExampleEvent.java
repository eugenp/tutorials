package com.baeldung.cdi2observers.events;

public class ExampleEvent {
    
   private final String eventMessage;

    public ExampleEvent(String eventMessage) {
        this.eventMessage = eventMessage;
    }
    
    public String getEventMessage() {
        return eventMessage;
    }
}
