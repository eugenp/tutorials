package com.baeldung.beaninjection.domain;

import org.springframework.beans.factory.annotation.Autowired;

public class ConsoleLogger implements Logger {

    private String messagePrefix;
    private static final String DEFAULT_MESSAGE_PREFIX = "LOG: ";
    
    @Override
    public void logMessage(String message, int maxChars) {
        System.out.println(getMessagePrefix() + message.substring(0, maxChars));
    }
    
    private String getMessagePrefix(){
        return this.messagePrefix == null ? DEFAULT_MESSAGE_PREFIX : messagePrefix;
    }

    @Autowired
    public void setMessagePrefix(String messagePrefix){
        this.messagePrefix = messagePrefix;
    }
}
