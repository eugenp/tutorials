package com.baeldung.springamqpsimple;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessages(String message) {
        rabbitTemplate.convertAndSend(SpringAmqpConfig.directQueueName, message);
        rabbitTemplate.convertAndSend(SpringAmqpConfig.fanoutExchangeName, "", message);
        rabbitTemplate.convertAndSend(SpringAmqpConfig.topicExchangeName, "user.not-important.info", message);
        rabbitTemplate.convertAndSend(SpringAmqpConfig.topicExchangeName, "user.important.error", message);
    }
}
