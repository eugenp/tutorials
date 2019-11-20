package com.baeldung.hexagonal.adapters;

import com.baeldung.hexagonal.domain.ChatMessage;
import com.baeldung.hexagonal.domain.IStoreMessages;

import java.util.Collection;
import java.util.Comparator;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * We're storing the messages in memory. We could later opt to switch to an implementation that stores messages in a
 * database.
 */
public class InMemoryMessageStore implements IStoreMessages {

    private Queue<ChatMessage> messages;

    public InMemoryMessageStore(Queue<ChatMessage> messages) {
        this.messages = messages;
    }

    @Override
    public void storeMessage(ChatMessage message) {
        this.messages.add(message);
    }

    @Override
    public Collection<ChatMessage> getMessages(long maxNbMessages) {// @formatter:off
        return messages.stream()
            .sorted((m1, m2) -> m2.getTimeSent().compareTo(m1.getTimeSent()))
            .limit(maxNbMessages)
            .sorted(Comparator.comparing(ChatMessage::getTimeSent))
            .collect(Collectors.toList());
        // @formatter:on
    }
}
