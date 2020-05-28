package com.baeldung.hexagon.adapters;

import com.baeldung.hexagon.core.domain.Message;
import com.baeldung.hexagon.core.ports.IMessageStore;

import java.util.ArrayList;
import java.util.List;

public class MessageStore implements IMessageStore {

    private List<Message> allMessages = new ArrayList<>();

    @Override
    public void saveMessage(Message message) {
        allMessages.add(message);
    }
}
