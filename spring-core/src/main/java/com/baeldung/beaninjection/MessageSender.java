package com.baeldung.beaninjection;

public class MessageSender {

    private ChatMessage message;

    public MessageSender(ChatMessage message) {
        this.message = message;
    }

    public void sendMessage() {
        message.send();
    }
}
