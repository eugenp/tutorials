package com.baeldung.hexagonal.adapters;

import com.baeldung.hexagonal.domain.User;
import com.baeldung.hexagonal.ports.MessageBus;
import com.baeldung.hexagonal.ports.SendMessagePort;
import org.springframework.stereotype.Service;
import com.baeldung.hexagonal.domain.Message;

/**
 * Send message adapter
 */
@Service
public class SendMessageService implements SendMessagePort {

    private MessageBus messageBus;

    public SendMessageService(MessageBus messageBus) {
        this.messageBus = messageBus;
    }

    @Override
    public boolean sendMessage(User user, Message message) {
        return messageBus.sendMessageToUser(user, message);
    }
}
