package com.baeldung.spring.jms;

import org.springframework.jms.core.JmsTemplate;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.TextMessage;
import java.util.Map;

public class SampleListener implements MessageListener {

    private JmsTemplate jmsTemplate;
    private Queue queue;

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void setQueue(Queue queue) {
        this.queue = queue;
    }

    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            try {
                String msg = ((TextMessage) message).getText();
                System.out.println("Received message: " + msg);
                if (msg == null) {
                    throw new IllegalArgumentException("Null value received...");
                }
            } catch (JMSException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public Employee receiveMessage() throws JMSException {
        Map map = (Map) this.jmsTemplate.receiveAndConvert();
        return new Employee((String) map.get("name"), (Integer) map.get("age"));
    }

}
