package com.baeldung.cdi.cdi2observers.observers;

import com.baeldung.cdi.cdi2observers.events.SimpleEvent;
import javax.annotation.Priority;
import javax.enterprise.event.Observes;

public class AnotherSimpleEventObserver {
    
    public String onEvent(@Observes @Priority(2) SimpleEvent event) {
        return event.getEventMessage();
    }   
}
