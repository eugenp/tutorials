package com.baeldung.cdi.cdi2observers.application;

import com.baeldung.cdi.cdi2observers.events.SimpleEvent;
import com.baeldung.cdi.cdi2observers.services.TextService;
import javax.enterprise.event.Observes;

public class TextApplication {
    
    public void onEvent(@Observes SimpleEvent event, TextService textService) {
        System.out.println(textService.parseText("Welcome to Baeldung!"));
    }    
}
