package com.baeldung.cdi.cdi2observers.observers;

import com.baeldung.cdi.cdi2observers.events.SimpleEvent;
import com.baeldung.cdi.cdi2observers.services.TextService;
import javax.annotation.Priority;
import javax.enterprise.event.Observes;

public class SimpleEventObserver {
    
    public String onEvent(@Observes @Priority(1) SimpleEvent event, TextService textService) {
        return textService.parseText(event.getEventMessage());
    } 
}
