package com.springinaction.messaging;

import java.math.BigDecimal;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class Sender {
  public static void main(String[] args) throws Exception {
    ApplicationContext context = new 
        ClassPathXmlApplicationContext("sender-context.xml");
    
    
    JmsTemplate template = (JmsTemplate) context.getBean("jmsTemplate");
    Destination queue = (Destination) context.getBean("queue");
    
    template.send(queue, new MessageCreator() {
      
      public Message createMessage(Session session)
              throws JMSException {
        Book book = new Book("Spring in Action", "Craig Walls", new BigDecimal(34.95));

        Message message = session.createObjectMessage(book);
        return message;
      }
    });
  }
}
