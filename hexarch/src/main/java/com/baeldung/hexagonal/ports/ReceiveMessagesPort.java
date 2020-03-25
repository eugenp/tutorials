package com.baeldung.hexagonal.ports;

import com.baeldung.hexagonal.domain.Message;
import com.baeldung.hexagonal.domain.User;

import java.util.List;

/**
 * Receive messages port
 */
public interface ReceiveMessagesPort {
    List<Message> receiveMessages(User user);
}
