package com.baeldung.generics;

public class EmailSender implements Sender {

    private String message;

    public EmailSender() {
    }

    public EmailSender(final String message) {
        this.message = message;
    }

    @Override
    public String send() {
        return "EMAIL";
    }
}
