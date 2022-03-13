package com.baeldung.spring.kafka;

import org.springframework.kafka.annotation.*;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(id = "userGreeting", topics = {"user"})
public class KafkaMultiListener {
    
    @KafkaHandler
    public void userListener(User user) {
        System.out.println("Received MultiListener " + user);
    }
    
    @KafkaHandler(isDefault = true)
    public void listenDefault(Object object) {
        System.out.println("Received MultiListener default " + object);
    }
}
