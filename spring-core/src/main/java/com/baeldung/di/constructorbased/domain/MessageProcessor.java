package com.baeldung.di.constructorbased.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.di.domain.EmailMessageSender;
import com.baeldung.di.domain.Message;
import com.baeldung.di.domain.MessageArchiver;
import com.baeldung.di.domain.MessageSender;
import com.baeldung.di.domain.TapeStorageMessageArchiver;

@Component
public class MessageProcessor {

    private MessageSender messageSender;

    private MessageArchiver messageArchiver;

    @Autowired
    MessageProcessor(EmailMessageSender messageSender, TapeStorageMessageArchiver messageArchiver) {
        this.messageSender = messageSender;
        this.messageArchiver = messageArchiver;
    }

    /**
    @Autowired
    MessageProcessor(MessageSender messageSender, MessageArchiver messageArchiver) {
        this.messageSender = messageSender;
        this.messageArchiver = messageArchiver;
    }
    */

    public void process(Message message) {
        // Business logic involving the message sender and message archiver
    }

    @Override
    public String toString() {
        return "MessageProcessor [messageSender=" + messageSender + ", messageArchiver=" + messageArchiver + "]";
    }

}
