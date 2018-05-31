package com.baeldung.reactive.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
//@AllArgsConstructor
public class Event {
    private String eventId;
    private String eventDt;
    
    public Event() {
        
    }
    
    public Event(String eventId, String eventDt) {
        super();
        this.eventId = eventId;
        this.eventDt = eventDt;
    }
    
    
}
