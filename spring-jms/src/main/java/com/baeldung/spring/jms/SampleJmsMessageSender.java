package com.baeldung.spring.jms;

import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

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
        this.jmsTemplate.send(this.queue, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("hello queue world");
            }
        });
    }

    public void sendMessage(final Employee employee) {
        System.out.println("Jms Message Sender : " + employee);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", employee.getName());
        map.put("age", employee.getAge());
        this.jmsTemplate.convertAndSend(map);
    }
}
