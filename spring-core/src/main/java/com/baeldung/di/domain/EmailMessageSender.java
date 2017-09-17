package com.baeldung.di.domain;

import org.springframework.stereotype.Component;

@Component
public class EmailMessageSender implements MessageSender {

    @Override
    public void send(Message message) {
        // Business logic
    }

    @Override
    public String toString() {
        return "EmailMessageSender";
    }

}
