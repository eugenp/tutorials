package com.baeldung.di.setterbased.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.di.domain.CloudStorageMessageArchiver;
import com.baeldung.di.domain.Message;
import com.baeldung.di.domain.MessageArchiver;
import com.baeldung.di.domain.MessageSender;
import com.baeldung.di.domain.SmsMessageSender;

@Component
public class MessageProcessor {

    private MessageSender messageSender;

    private MessageArchiver messageArchiver;

    @Autowired
    public void setMessageSender(SmsMessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Autowired
    public void setMessageArchiver(CloudStorageMessageArchiver messageArchiver) {
        this.messageArchiver = messageArchiver;
    }

    public void process(Message message) {
        // Business logic involving the message sender and message archiver
    }

    @Override
    public String toString() {
        return "MessageProcessor [messageSender=" + messageSender + ", messageArchiver=" + messageArchiver + "]";
    }

}
