package com.baeldung.spring.jms;

import org.springframework.jms.core.JmsTemplate;

import javax.jms.Queue;
import java.util.HashMap;
import java.util.Map;

public class SampleJmsMessageSender {

    private JmsTemplate jmsTemplate;
    private Queue queue;

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void setQueue(Queue queue) {
        this.queue = queue;
    }

    public void simpleSend() {
        jmsTemplate.send(queue, s -> s.createTextMessage("hello queue world"));
    }

    public void sendMessage(final Employee employee) {
        this.jmsTemplate.convertAndSend(employee);
    }

    public void sendTextMessage(String msg) {
        this.jmsTemplate.send(queue, s -> s.createTextMessage(msg));
    }
}
