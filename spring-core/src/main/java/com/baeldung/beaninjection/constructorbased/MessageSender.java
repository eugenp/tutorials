package com.baeldung.beaninjection.constructorbased;

import com.baeldung.beaninjection.ChatMessage;

public class MessageSender {

    private ChatMessage message;

    public ChatMessage getMessage() {
        return message;
    }

    public void setMessage(ChatMessage message) {
        this.message = message;
    }

    public MessageSender(ChatMessage message) {
        this.message = message;
    }

    public void sendMessage() {
        message.send();
    }
}
