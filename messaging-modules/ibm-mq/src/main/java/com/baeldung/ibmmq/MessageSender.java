package com.baeldung.ibmmq;

import static java.rmi.server.LogStream.log;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

public class MessageSender {

    private QueueConnectionFactory factory;

    public MessageSender() throws JMSException {
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

    public QueueSender createSender(QueueSession session, Queue queue) throws JMSException {
        return session.createSender(queue);
    }

    public void sendMessage(String messageText) {
        QueueConnection connection = null;
        QueueSession session = null;
        QueueSender sender = null;
        try {
            connection = createConnection();
            session = createSession(connection);
            Queue queue = getQueue(session);
            sender = createSender(session, queue);
            connection.start();
            TextMessage message = session.createTextMessage();
            message.setText(messageText);
            sender.send(message);
            log("Message sent: " + messageText);
        } catch (JMSException e) {
            // handle exception
        } finally {
            try {
                if (sender != null) {
                    sender.close();
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
