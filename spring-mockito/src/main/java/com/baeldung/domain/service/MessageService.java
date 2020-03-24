package com.baeldung.domain.service;

import com.baeldung.domain.model.Message;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    public Message deliverMessage (Message message) {

        return message;
    }
}
