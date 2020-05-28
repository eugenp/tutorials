package com.baeldung.hexagon.core.services;

import com.baeldung.hexagon.core.domain.Message;
import com.baeldung.hexagon.core.ports.IMessageOutput;
import com.baeldung.hexagon.core.ports.IMessageStore;

public class MessagingService {

    private IMessageOutput receiver;
    private IMessageStore store;

    public MessagingService(IMessageOutput receiver, IMessageStore store) {
        this.receiver = receiver;
        this.store = store;
    }

    public void sendMessage(Message message) {
        store.saveMessage(message);
        IMessageOutput.ReceiveMessageCommand command 
            = new IMessageOutput.ReceiveMessageCommand(message.getSender(), message.getReceiver(), message.getContent());
        receiver.receive(command);
    }
}
