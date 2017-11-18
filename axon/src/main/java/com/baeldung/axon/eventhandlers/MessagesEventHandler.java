package com.baeldung.axon.eventhandlers;

import com.baeldung.axon.events.MessageReadEvent;
import com.baeldung.axon.events.MessageCreatedEvent;
import org.axonframework.eventhandling.EventHandler;


public class MessagesEventHandler {

    @EventHandler
    public void handle(MessageCreatedEvent event) {
        System.out.println("Message received: " + event.getText() + " (" + event.getId() + ")");
    }

    @EventHandler
    public void handle(MessageReadEvent event) {
        System.out.println("Message read: " + event.getId());
    }
}