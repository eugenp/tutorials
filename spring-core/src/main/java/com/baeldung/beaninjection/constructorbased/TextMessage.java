package com.baeldung.beaninjection.constructorbased;

public class TextMessage implements ChatMessage {

    String message;
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void send() {
        // TODO Auto-generated method stub
    }

}
