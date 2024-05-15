package com.baeldung.mockito.argumentmatchers.service;

import org.springframework.stereotype.Service;

import com.baeldung.mockito.argumentmatchers.Message;

@Service
public class MessageService {

    public Message deliverMessage(Message message) {

        return message;
    }
}
