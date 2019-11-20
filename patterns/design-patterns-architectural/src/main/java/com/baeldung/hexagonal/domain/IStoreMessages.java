package com.baeldung.hexagonal.domain;

import java.util.Collection;

/**
 * An external system (infrastructure) stores a message
 */
public interface IStoreMessages {

    void storeMessage(ChatMessage message);

    Collection<ChatMessage> getMessages(long maxNbMessages);

}
