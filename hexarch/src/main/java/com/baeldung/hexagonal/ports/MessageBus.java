package com.baeldung.hexagonal.ports;

import com.baeldung.hexagonal.domain.Message;
import com.baeldung.hexagonal.domain.User;

import java.util.List;

/**
 * Message bus interface
 */
public interface MessageBus {

    /**
     * Send message to user
     * @param user recipient
     * @param message object to send
     * @return true if successful
     */
    boolean sendMessageToUser(User user, Message message);

    /**
     * Fetch unread messages
     * @param user addres
     * @return list of messages
     */
    List<Message> getUserMessages(User user);
}
