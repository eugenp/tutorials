package com.baeldung.axon.eventhandlers;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

import com.baeldung.axon.events.MessageCreatedEvent;
import com.baeldung.axon.events.MessageReadEvent;

@Service
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