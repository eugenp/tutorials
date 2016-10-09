package com.baeldung.spring.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class SampleListener implements MessageListener {

    private String textMessage;

    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            try {
                textMessage = ((TextMessage) message).getText();
            } catch (JMSException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            throw new IllegalArgumentException("Message Error");
        }
    }
}