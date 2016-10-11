package com.baeldung.spring.jms;

import org.springframework.jms.core.JmsTemplate;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.Map;

public class SampleListener implements MessageListener {

    private JmsTemplate jmsTemplate;
    public JmsTemplate getJmsTemplate() {
        this.jmsTemplate = jmsTemplate;
    }

    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            try {

                String msg = ((TextMessage) message).getText();
            } catch (JMSException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            throw new IllegalArgumentException("Message Error");
        }
    }

    public Employee receiveMessage() throws JMSException {
        Map map = (Map) getJmsTemplate().receiveAndConvert();
        return new Employee((String) map.get("name"), (Integer) map.get("age"));
    }
}
