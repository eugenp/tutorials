package com.baeldung.spring.jms.testing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.junit.EmbeddedActiveMQBroker;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { EmbeddedActiveMqIntegrationTest.TestConfiguration.class })
public class EmbeddedActiveMqIntegrationTest {

    @ClassRule
    public static EmbeddedActiveMQBroker embeddedBroker = new EmbeddedActiveMQBroker();

    @SpyBean
    private MessageListener messageListener;

    @SpyBean
    private MessageSender messageSender;

    @Test
    public void whenListening_thenReceivingCorrectMessage() throws JMSException {
        String queueName = "queue-1";
        String messageText = "Test message";

        assertEquals(0, embeddedBroker.getDestination(queueName).getDestinationStatistics().getDispatched().getCount());
        assertEquals(0, embeddedBroker.getDestination(queueName).getDestinationStatistics().getMessages().getCount());

        embeddedBroker.pushMessage(queueName, messageText);

        ArgumentCaptor<TextMessage> messageCaptor = ArgumentCaptor.forClass(TextMessage.class);
        Mockito.verify(messageListener, Mockito.timeout(100))
            .sampleJmsListenerMethod(messageCaptor.capture());

        TextMessage receivedMessage = messageCaptor.getValue();
        assertEquals(messageText, receivedMessage.getText());

        assertEquals(1, embeddedBroker.getDestination(queueName).getDestinationStatistics().getDispatched().getCount());
        assertEquals(0, embeddedBroker.getDestination(queueName).getDestinationStatistics().getMessages().getCount());
    }

    @Test
    public void whenSendingMessage_thenCorrectQueueAndMessageText() throws JMSException {
        String queueName = "queue-2";
        String messageText = "Test message";

        messageSender.sendTextMessage(queueName, messageText);
        assertEquals(1, embeddedBroker.getMessageCount(queueName));
        TextMessage sentMessage = embeddedBroker.peekTextMessage(queueName);
        assertEquals(messageText, sentMessage.getText());
    }

    @Configuration
    @EnableJms
    static class TestConfiguration {
        @Bean
        public JmsListenerContainerFactory<?> jmsListenerContainerFactory() {
            DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
            factory.setConnectionFactory(connectionFactory());
            return factory;
        }

        @Bean
        public ConnectionFactory connectionFactory() {
            return new ActiveMQConnectionFactory(embeddedBroker.getVmURL());
        }

        @Bean
        public JmsTemplate jmsTemplate() {
            return new JmsTemplate(connectionFactory());
        }
    }

}
