package com.baeldung.hexagonal.adapters;

import com.baeldung.hexagonal.ports.MessageBus;
import org.springframework.stereotype.Service;
import com.baeldung.hexagonal.domain.Message;
import com.baeldung.hexagonal.domain.User;

import java.util.*;

/**
 * Message bus service
 */
@Service
public class InMemoryMessageBus implements MessageBus {
    private Map<User, List<Message>> messages = new HashMap<>();

    public InMemoryMessageBus() {
        User alice = new User("alice");
        User bob = new User("bob");

        messages.put(alice, new ArrayList<>());
        messages.put(bob, new ArrayList<>());
    }

    @Override
    public boolean sendMessageToUser(User user, Message message) {
        return messages.containsKey(user)
                ? messages.get(user).add(message)
                : false;
    }

    @Override
    public List<Message> getUserMessages(User user) {
        if (messages.containsKey(user)) {
            // We need to copy items to mark them as 'sent' (i.e. clear user messages)
            List<Message> unreadMessages = new ArrayList<>(messages.get(user));
            messages.get(user).clear();
            return unreadMessages;
        }
        return Collections.emptyList();
    }
}
