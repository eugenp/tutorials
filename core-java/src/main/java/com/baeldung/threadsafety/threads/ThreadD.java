package com.baeldung.threadsafety.threads;

import com.baeldung.threadsafety.services.MessageService;

public class ThreadD extends Thread {
    
    private final MessageService messageService;
    
    public ThreadD(MessageService messageService) {
        this.messageService = messageService;
    }
    
    @Override
    public void run() {
        System.out.println(messageService.getMesssage());
    }
    
    public MessageService getMessageService() {
        return messageService;
    }
}