package com.baeldung.cdi.cdi2observers.observers;

import com.baeldung.cdi.cdi2observers.events.ExampleEvent;
import javax.annotation.Priority;
import javax.enterprise.event.Observes;

public class AnotherExampleEventObserver {
    
    public String onEvent(@Observes @Priority(2) ExampleEvent event) {
        return event.getEventMessage();
    }   
}
