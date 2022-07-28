package com.baeldung.spring.jms.testing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.assertj.core.api.Assertions;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

import com.baeldung.spring.jms.testing.TestContainersActiveMqIntegrationTest.TestConfiguration;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class, MessageSender.class })
public class TestContainersActiveMqIntegrationTest {

    @ClassRule
    public static GenericContainer<?> activeMqContainer = new GenericContainer<>(DockerImageName.parse("rmohr/activemq:5.14.3")).withExposedPorts(61616);

    @SpyBean
    private MessageListener messageListener;

    @Autowired
    private MessageSender messageSender;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Test
    public void whenListening_thenReceivingCorrectMessage() throws JMSException {
        String queueName = "queue-1";
        String messageText = "Test message";

        jmsTemplate.send(queueName, s -> s.createTextMessage(messageText));

        ArgumentCaptor<TextMessage> messageCaptor = ArgumentCaptor.forClass(TextMessage.class);

        Mockito.verify(messageListener, Mockito.timeout(100))
            .sampleJmsListenerMethod(messageCaptor.capture());

        TextMessage receivedMessage = messageCaptor.getValue();
        assertEquals(messageText, receivedMessage.getText());
    }

    @Test
    public void whenSendingMessage_thenCorrectQueueAndMessageText() throws JMSException {
        String queueName = "queue-2";
        String messageText = "Test message";

        messageSender.sendTextMessage(queueName, messageText);

        Message sentMessage = jmsTemplate.receive(queueName);
        Assertions.assertThat(sentMessage)
            .isInstanceOf(TextMessage.class);

        assertEquals(messageText, ((TextMessage) sentMessage).getText());
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
            String brokerUrlFormat = "tcp://%s:%d";
            String brokerUrl = String.format(brokerUrlFormat, activeMqContainer.getHost(), activeMqContainer.getFirstMappedPort());
            return new ActiveMQConnectionFactory(brokerUrl);
        }

        @Bean
        public JmsTemplate jmsTemplate() {
            return new JmsTemplate(connectionFactory());
        }
    }

}
