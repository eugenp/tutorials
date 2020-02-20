package com.baeldung.ejb.spring.comparison.spring.messagedriven;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class Receiver {
    @Autowired
    private JmsTemplate jmsTemplate;

    @JmsListener(destination = "myQueue")
    public void receiveMessage(String msg) {
        sendAck();
    }

    private void sendAck() {
        jmsTemplate.convertAndSend("ackQueue", "polo");
    }

}