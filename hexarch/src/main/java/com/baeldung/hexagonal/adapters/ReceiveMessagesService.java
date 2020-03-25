package com.baeldung.hexagonal.adapters;

import com.baeldung.hexagonal.ports.MessageBus;
import com.baeldung.hexagonal.ports.ReceiveMessagesPort;
import org.springframework.stereotype.Service;
import com.baeldung.hexagonal.domain.Message;
import com.baeldung.hexagonal.domain.User;

import java.util.List;

/**
 * Receive messages adapter
 */
@Service
public class ReceiveMessagesService implements ReceiveMessagesPort {

    private MessageBus messageBus;

    public ReceiveMessagesService(MessageBus messageBus) {
        this.messageBus = messageBus;
    }

    @Override
    public List<Message> receiveMessages(User user) {
        return messageBus.getUserMessages(user);
    }
}
