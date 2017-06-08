package com.baeldung.beaninjection.constructorbased;

public class MessageSender {

    private ChatMessage message;

    public MessageSender(ChatMessage message) {
        this.message = message;
    }

    public void sendMessage() {
        message.send();
    }
}
