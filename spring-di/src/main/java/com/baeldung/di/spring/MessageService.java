package com.baeldung.di.spring;

public class MessageService implements IService {

    private String message;

    public MessageService(String message) {
        this.message = message;
    }

    @Override
    public String serve() {
        return message;
    }

}
