package com.springinaction.messaging;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

public class ConventionalReceiver {
  public static void main(String[] args) {
    //<start id="conventional_message_receiver"/>  
    ConnectionFactory cf = 
        new ActiveMQConnectionFactory("tcp://localhost:61616");
    Connection conn = null;
    Session session = null;
    try {
      conn = cf.createConnection();
      conn.start();
      session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
      Destination destination = 
          new ActiveMQQueue("spitter.queue");
      MessageConsumer consumer = session.createConsumer(destination);
      Message message = consumer.receive();
      TextMessage textMessage = (TextMessage) message;
      System.out.println("GOT A MESSAGE: " + textMessage.getText());
      conn.start();
    } catch (JMSException e) {
      // handle exception?
    } finally {
      try {
        if (session != null) {
          session.close();
        }
        if (conn != null) {
          conn.close();
        }
      } catch (JMSException ex) {
      }
    }
    //<end id="conventional_message_receiver"/>  
  }
}
