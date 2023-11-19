package com.baeldung.pubsubmq.client;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class ClientApplication {
    private static final String MESSAGE_QUEUE = "pizza-message-queue";

    @Bean
    public Queue queue() {
        return new Queue(MESSAGE_QUEUE);
    }

    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(MESSAGE_QUEUE);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    public Consumer consumer() {
        return new Consumer();
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(Consumer consumer) {
        return new MessageListenerAdapter(consumer, "receiveOrder");
    }

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }
}
