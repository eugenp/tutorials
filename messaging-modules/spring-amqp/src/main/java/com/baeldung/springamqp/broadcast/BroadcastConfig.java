package com.baeldung.springamqp.broadcast;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BroadcastConfig {

    private static final boolean NON_DURABLE = false;

    public final static String FANOUT_QUEUE_1_NAME = "com.baeldung.spring-amqp-simple.fanout.queue1";
    public final static String FANOUT_QUEUE_2_NAME = "com.baeldung.spring-amqp-simple.fanout.queue2";
    public final static String FANOUT_EXCHANGE_NAME = "com.baeldung.spring-amqp-simple.fanout.exchange";

    public final static String TOPIC_QUEUE_1_NAME = "com.baeldung.spring-amqp-simple.topic.queue1";
    public final static String TOPIC_QUEUE_2_NAME = "com.baeldung.spring-amqp-simple.topic.queue2";
    public final static String TOPIC_EXCHANGE_NAME = "com.baeldung.spring-amqp-simple.topic.exchange";
    public static final String BINDING_PATTERN_IMPORTANT = "*.important.*";
    public static final String BINDING_PATTERN_ERROR = "#.error";

    @Bean
    public Declarables topicBindings() {
        Queue topicQueue1 = new Queue(TOPIC_QUEUE_1_NAME, NON_DURABLE);
        Queue topicQueue2 = new Queue(TOPIC_QUEUE_2_NAME, NON_DURABLE);

        TopicExchange topicExchange = new TopicExchange(TOPIC_EXCHANGE_NAME, NON_DURABLE, false);

        return new Declarables(topicQueue1, topicQueue2, topicExchange, BindingBuilder
          .bind(topicQueue1)
          .to(topicExchange)
          .with(BINDING_PATTERN_IMPORTANT), BindingBuilder
          .bind(topicQueue2)
          .to(topicExchange)
          .with(BINDING_PATTERN_ERROR));
    }

    @Bean
    public Declarables fanoutBindings() {
        Queue fanoutQueue1 = new Queue(FANOUT_QUEUE_1_NAME, NON_DURABLE);
        Queue fanoutQueue2 = new Queue(FANOUT_QUEUE_2_NAME, NON_DURABLE);

        FanoutExchange fanoutExchange = new FanoutExchange(FANOUT_EXCHANGE_NAME, NON_DURABLE, false);

        return new Declarables(fanoutQueue1, fanoutQueue2, fanoutExchange, BindingBuilder
          .bind(fanoutQueue1)
          .to(fanoutExchange), BindingBuilder
          .bind(fanoutQueue2)
          .to(fanoutExchange));
    }

}
