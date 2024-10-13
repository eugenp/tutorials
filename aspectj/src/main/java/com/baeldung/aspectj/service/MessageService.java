package com.baeldung.aspectj.service;

import org.springframework.stereotype.Service;

@Service
public class MessageService {
    public void sendMessage(String message) {
        System.out.println("sending message: " + message);
    }

    public void receiveMessage(String message) {
        System.out.println("receiving message: " + message);
    }
}
