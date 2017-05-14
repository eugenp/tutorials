package com.baeldung.spring.di;

public class FacebookService implements MessageService {

    public MessageService getMessageService() {
        return this;
    }
}
