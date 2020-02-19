package com.baeldung.ejb.spring.comparison.ejb.messagedriven;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

@MessageDriven(activationConfig = { @ActivationConfigProperty(propertyName = "destination", propertyValue = "myQueue"), @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") })
public class RecieverMDB implements MessageListener {

    @Resource
    private ConnectionFactory connectionFactory;

    @Resource(name = "ackQueue")
    private Queue ackQueue;

    public void onMessage(Message message) {
        try {

            TextMessage textMessage = (TextMessage) message;
            String producerPing = textMessage.getText();

            if (producerPing.equals("marco")) {
                acknowledge("polo");
            }
        } catch (JMSException e) {
            throw new IllegalStateException(e);
        }
    }

    private void acknowledge(String text) throws JMSException {

        Connection connection = null;
        Session session = null;

        try {
            connection = connectionFactory.createConnection();
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            MessageProducer ackSender = session.createProducer(ackQueue);
            ackSender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            TextMessage message = session.createTextMessage(text);

            ackSender.send(message);
        } finally {
            session.close();
            connection.close();
        }
    }

}
