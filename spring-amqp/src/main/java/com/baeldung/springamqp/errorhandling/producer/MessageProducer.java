package com.baeldung.springamqp.errorhandling.producer;

import com.baeldung.springamqp.errorhandling.configuration.SimpleDLQAmqpConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {

    private static final Logger log = LoggerFactory.getLogger(MessageProducer.class);
    private int messageNumber = 0;
    private final RabbitTemplate rabbitTemplate;

    public MessageProducer(final RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage() {
        log.info("Sending message...");
        rabbitTemplate.convertAndSend(SimpleDLQAmqpConfiguration.EXCHANGE_MESSAGES, SimpleDLQAmqpConfiguration.QUEUE_MESSAGES, "Some message id:" + messageNumber++);
    }
}