package com.baeldung.pubsubmq.server;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

import javax.annotation.PostConstruct;

public class Publisher {

    private RabbitTemplate rabbitTemplate;
    private String queue;
    private String topic;

    public Publisher(RabbitTemplate rabbitTemplate, String queue, String topic) {
        this.rabbitTemplate = rabbitTemplate;
        this.queue = queue;
        this.topic = topic;
    }

    @PostConstruct
    public void postMessages() {
        rabbitTemplate.convertAndSend(queue, "1 Pepperoni");
        rabbitTemplate.convertAndSend(queue, "3 Margarita");
        rabbitTemplate.convertAndSend(queue, "1 Ham and Pineapple (yuck)");

        rabbitTemplate.convertAndSend(topic, "notification", "New Deal on T-Shirts: 95% off!");
        rabbitTemplate.convertAndSend(topic, "notification", "2 for 1 on all Jeans!");
    }
}
