package com.baeldung.event.listener;

import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class ContextEventListener {

    @Order(2)
    @EventListener
    public void handleContextRefreshEvent(ContextStartedEvent ctxStartEvt) {
        System.out.println("Context Start Event received.");
    }

    @Order(1)
    @EventListener(classes = { ContextStartedEvent.class, ContextStoppedEvent.class })
    public void handleMultipleEvents() {
        System.out.println("Multi-event listener invoked");
    }

}
