package com.baeldung.cdi.cdi2observers.events;

import javax.enterprise.event.Event;
import javax.inject.Inject;

public class ExampleEventSource {
    
    @Inject
    Event<ExampleEvent> exampleEvent;
    
    public void fireEvent() {
        simpleEvent.fireAsync(new ExampleEvent("Welcome to Baeldung!"));
    }   
}
