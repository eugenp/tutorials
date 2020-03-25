package com.baeldung.hexagonal.ports;

import com.baeldung.hexagonal.domain.Message;
import com.baeldung.hexagonal.domain.User;

/**
 * Send message port
 */
public interface SendMessagePort {
    boolean sendMessage(User user, Message message);
}
