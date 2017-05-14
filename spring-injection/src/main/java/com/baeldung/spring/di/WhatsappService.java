package com.baeldung.spring.di;

public class WhatsappService implements MessageService {

    public MessageService getMessageService() {
        return this;
    }
}
