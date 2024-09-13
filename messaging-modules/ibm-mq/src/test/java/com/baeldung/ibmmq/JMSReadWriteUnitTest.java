package com.baeldung.ibmmq;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class JMSReadWriteUnitTest {
    @Mock
    private QueueConnectionFactory factory;
    @Mock
    private QueueConnection connection;
    @Mock
    private QueueSession session;
    @Mock
    private Queue queue;
    @Mock
    private QueueSender sender;
    @Mock
    private QueueReceiver receiver;
    @Mock
    private TextMessage textMessage;
    @InjectMocks
    private MessageSender messageSender;
    @InjectMocks
    private MessageReceiver messageReceiver;

    @BeforeEach
    public void setup() throws JMSException {
        MockitoAnnotations.openMocks(this);
        when(factory.createQueueConnection()).thenReturn(connection);
        when(connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE)).thenReturn(session);
        when(session.createQueue("QUEUE1")).thenReturn(queue);
        when(session.createSender(queue)).thenReturn(sender);
        when(session.createReceiver(queue)).thenReturn(receiver);
        when(session.createTextMessage()).thenReturn(textMessage);
    }

    @Test
    public void whenSendMessage_thenMessageIsSentSuccessfully() throws JMSException {
        String messageText = "Hello Baeldung! Nice to meet you!";
        doNothing().when(sender).send(any(TextMessage.class));

        messageSender.sendMessage(messageText);

        verify(sender).send(any(TextMessage.class));
        verify(textMessage).setText(messageText);
    }

    @Test
    public void whenReceiveMessage_thenMessageIsReceivedSuccessfully() throws JMSException {
        when(receiver.receive(anyLong())).thenReturn(textMessage);
        when(textMessage.getText()).thenReturn("Hello Baeldung! Nice to meet you!");

        messageReceiver.receiveMessage();

        verify(receiver).receive(anyLong());
        verify(textMessage).getText();
    }
}
