package com.baeldung.hexagon.adapters;

import com.baeldung.hexagon.core.ports.IMessageOutput;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;

public class ClientOutputAdapter implements IMessageOutput {

    private Map<String, MessageHandler> clients = new HashMap<>();

    public void register(String userName, MessageHandler handler) {
        clients.put(userName, handler);
    }

    @Override
    public void receive(ReceiveMessageCommand command) {
        MessageHandler client = clients.get(command.getReceiver());
        if (nonNull(client)) {
            client.receive(String.format("Message From %s: %s", command.getSender(), command.getContent()));
        }
    }

    public interface MessageHandler {
        void receive(String message);
    }
}
