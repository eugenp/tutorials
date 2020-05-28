package com.baeldung.hexagon.adapters;

import com.baeldung.hexagon.core.domain.Message;
import com.baeldung.hexagon.core.ports.IMessageInput;
import com.baeldung.hexagon.core.services.MessagingService;

import java.util.UUID;

public class ClientInputAdapter implements IMessageInput {

    private MessagingService service;

    public ClientInputAdapter(MessagingService service) {
        this.service = service;
    }

    @Override
    public void post(PostMessageCommand command) {
        String id = UUID.randomUUID().toString();
        Message message = new Message(id, command.getSender(), command.getReceiver(), command.getContent());
        service.sendMessage(message);
    }
}
