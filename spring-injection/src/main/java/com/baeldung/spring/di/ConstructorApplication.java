package com.baeldung.spring.di;


public class ConstructorApplication {

    private MessageService messageService;

    public ConstructorApplication(MessageService service) {
        this.messageService = service;
    }

    public MessageService getMessageService() {
        return messageService;
    }
}
