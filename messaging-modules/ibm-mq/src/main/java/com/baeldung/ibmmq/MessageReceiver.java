package com.baeldung.ibmmq;

import static java.rmi.server.LogStream.log;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.Queue;
import javax.jms.TextMessage;

public class MessageReceiver {

    private QueueConnectionFactory factory;

    public MessageReceiver() throws JMSException {
        factory = new JMSSetup().createConnectionFactory();
    }

    public QueueConnection createConnection() throws JMSException {
        return factory.createQueueConnection();
    }

    public QueueSession createSession(QueueConnection connection) throws JMSException {
        return connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    public Queue getQueue(QueueSession session) throws JMSException {
        return session.createQueue("QUEUE1");
    }

    public QueueReceiver createReceiver(QueueSession session, Queue queue) throws JMSException {
        return session.createReceiver(queue);
    }

    public void receiveMessage() {
        QueueConnection connection = null;
        QueueSession session = null;
        QueueReceiver receiver = null;
        try {
            connection = createConnection();
            session = createSession(connection);
            Queue queue = getQueue(session);
            receiver = createReceiver(session, queue);
            connection.start();
            Message message = receiver.receive(1000);
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                long timestamp = message.getJMSTimestamp();
                String messageId = message.getJMSMessageID();
                int priority = message.getJMSPriority();
                long expiration = message.getJMSExpiration();
                log("Message received: " + textMessage.getText());
                log("Message ID: " + messageId);
                log("Message timestamp: " + timestamp);
                log("Message Priority: " + priority);
                log("Expiration Time: " + expiration);
            } else {
                log("No text message received.");
            }
            // log("Message sent: " + messageText);
        } catch (JMSException e) {
            // handle exception
        } finally {
            try {
                if (receiver != null) {
                    receiver.close();
                }

                if (session != null) {
                    session.close();
                }

                if (connection != null) {
                    connection.close();
                }
            } catch (JMSException e) {
                // handle exception
            }
        }
    }
}