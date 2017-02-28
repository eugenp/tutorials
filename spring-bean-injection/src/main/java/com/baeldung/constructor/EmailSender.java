package com.baeldung.constructor;

public class EmailSender {
    private String message;

    public EmailSender(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
