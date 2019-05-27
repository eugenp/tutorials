package com.baeldung.hexagonalarchitecture.services;

import com.baeldung.hexagonalarchitecture.models.Message;
import com.baeldung.hexagonalarchitecture.repositories.MessageDAOInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class FBMessageService implements MessageService {

    // uses messageDAOInterface to save message to DB
    @Autowired
    MessageDAOInterface messageDAOInterface;

    @Override
    public Message saveMessage(String message) {
        Message messageFromDB = messageDAOInterface.save(new Message(message, Message.MESSAGE_SOURCE_FB));
        return messageFromDB;
    }
    @Override
    public Iterable<Message> getMessages() {
        return messageDAOInterface.findMessagesBySource(Message.MESSAGE_SOURCE_FB);
    }
}
