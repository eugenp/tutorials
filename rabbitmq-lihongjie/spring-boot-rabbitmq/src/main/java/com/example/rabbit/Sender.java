package com.example.rabbit;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Sender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send() {
        String content = "hello " + new Date();
        System.out.println("Sender: " + content);
        this.rabbitTemplate.convertAndSend("hello-test", content);
    }
}
