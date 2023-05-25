package com.baeldung.spring.jms.testing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {

    @Autowired
    private JmsTemplate jmsTemplate;

    private static final Logger logger = LoggerFactory.getLogger(MessageSender.class);

    public void sendTextMessage(String destination, String message) {
        logger.info("Sending message to {} destination with text {}", destination, message);
        jmsTemplate.send(destination, s -> s.createTextMessage(message));
    }
}
