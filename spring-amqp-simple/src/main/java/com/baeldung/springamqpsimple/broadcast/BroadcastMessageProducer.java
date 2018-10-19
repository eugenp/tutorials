package com.baeldung.springamqpsimple.broadcast;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BroadcastMessageProducer {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public BroadcastMessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessages(String message) {
        rabbitTemplate.convertAndSend(BroadcastConfig.fanoutExchangeName, "", message);
        rabbitTemplate.convertAndSend(BroadcastConfig.topicExchangeName, "user.not-important.info", message);
        rabbitTemplate.convertAndSend(BroadcastConfig.topicExchangeName, "user.important.error", message);
    }
}
