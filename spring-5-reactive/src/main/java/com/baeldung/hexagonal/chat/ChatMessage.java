package com.baeldung.hexagonal.chat;

import java.util.List;
import java.util.ArrayList;

class ChatMessage {
    private String message;
    private List<String> recipients;

    public ChatMessage(String message, List<String> recipients) {
        this.message = message;
        this.recipients = new ArrayList<String>(recipients);
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getRecipients() {
        return this.recipients;
    }

    public void addRecipient(String recipient) {
        this.recipients.add(recipient);
    }
}
