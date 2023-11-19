package com.baeldung.pubsubmq.server;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServerApplication {
    private static final String MESSAGE_QUEUE = "pizza-message-queue";
    private static final String PUB_SUB_TOPIC = "notification-topic";
    private static final String PUB_SUB_EMAIL_QUEUE = "email-queue";
    private static final String PUB_SUB_TEXT_QUEUE = "text-queue";

    @Bean
    public Queue queue() {
        return new Queue(MESSAGE_QUEUE);
    }

    @Bean
    public Queue emailQueue() {
        return new Queue(PUB_SUB_EMAIL_QUEUE);
    }

    @Bean
    public Queue textQueue() {
        return new Queue(PUB_SUB_TEXT_QUEUE);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(PUB_SUB_TOPIC);
    }

    @Bean
    public Binding emailBinding(Queue emailQueue, TopicExchange exchange) {
        return BindingBuilder.bind(emailQueue).to(exchange).with("notification");
    }

    @Bean
    public Binding textBinding(Queue textQueue, TopicExchange exchange) {
        return BindingBuilder.bind(textQueue).to(exchange).with("notification");
    }

    @Bean
    public Publisher publisher(RabbitTemplate rabbitTemplate) {
        return new Publisher(rabbitTemplate, MESSAGE_QUEUE, PUB_SUB_TOPIC);
    }

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}
