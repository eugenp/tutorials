package com.baeldung.hexagonalarchitecture.services;

import com.baeldung.hexagonalarchitecture.models.Message;


public interface MessageService {
    public Message saveMessage(String message);
    public Iterable<Message> getMessages();
}
