package com.baeldung.ejb.spring.comparison.spring.messagedriven;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer {
    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendMessageToDefaultDestination(final String message) {
        jmsTemplate.convertAndSend("myQueue", message);
    }

    public String receiveAck() {
        return (String) jmsTemplate.receiveAndConvert("ackQueue");
    }
}