package com.baeldung.spring.di;

import org.springframework.beans.factory.annotation.Autowired;

public class SetterApplication {

    private MessageService messageService;

    public MessageService getMessageService() {
        return messageService;
    }

    @Autowired
    public void setMessageService(MessageService service) {
        this.messageService = service;
    }
}
