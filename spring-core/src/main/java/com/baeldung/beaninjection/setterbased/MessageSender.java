package com.baeldung.beaninjection.setterbased;

import com.baeldung.beaninjection.ChatMessage;

public class MessageSender {

    private ChatMessage message;

    public MessageSender() {
    }
    
    public MessageSender(ChatMessage message) {
        this.message = message;
    }
    
    public ChatMessage getMessage() {
        return message;
    }

    public void setMessage(ChatMessage message) {
        this.message = message;
    }

    public void sendMessage() {
        message.send();
    }
}
