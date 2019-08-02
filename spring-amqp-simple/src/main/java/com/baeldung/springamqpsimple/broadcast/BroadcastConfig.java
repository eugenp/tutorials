package com.baeldung.springamqpsimple.broadcast;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;
import java.util.List;

@Configuration
public class BroadcastConfig {

    public final static String fanoutQueue1Name = "com.baeldung.spring-amqp-simple.fanout.queue1";
    public final static String fanoutQueue2Name = "com.baeldung.spring-amqp-simple.fanout.queue2";
    public final static String fanoutExchangeName = "com.baeldung.spring-amqp-simple.fanout.exchange";

    public final static String topicQueue1Name = "com.baeldung.spring-amqp-simple.topic.queue1";
    public final static String topicQueue2Name = "com.baeldung.spring-amqp-simple.topic.queue2";
    public final static String topicExchangeName = "com.baeldung.spring-amql-simple.topic.exchange";

    @Bean
    public Declarables topicBindings() {
        Queue topicQueue1 = new Queue(topicQueue1Name, false);
        Queue topicQueue2 = new Queue(topicQueue2Name, false);

        TopicExchange topicExchange = new TopicExchange(topicExchangeName);

        return new Declarables(
                topicQueue1,
                topicQueue2,
                topicExchange,
                BindingBuilder.bind(topicQueue1).to(topicExchange).with("*.important.*"),
                BindingBuilder.bind(topicQueue2).to(topicExchange).with("user.#"));
    }

    @Bean
    public Declarables fanoutBindings() {
        Queue fanoutQueue1 = new Queue(fanoutQueue1Name, false);
        Queue fanoutQueue2 = new Queue(fanoutQueue2Name, false);

        FanoutExchange fanoutExchange = new FanoutExchange(fanoutExchangeName);

        return new Declarables(
                fanoutQueue1,
                fanoutQueue2,
                fanoutExchange,
                BindingBuilder.bind(fanoutQueue1).to(fanoutExchange),
                BindingBuilder.bind(fanoutQueue2).to(fanoutExchange)
        );
    }

    @Bean
    public SimpleRabbitListenerContainerFactory broadcastContainer(ConnectionFactory connectionFactory, SimpleRabbitListenerContainerFactoryConfigurer configurer) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }

}
