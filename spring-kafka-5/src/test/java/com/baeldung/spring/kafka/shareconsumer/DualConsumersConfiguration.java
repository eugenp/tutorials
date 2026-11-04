package com.baeldung.spring.kafka.shareconsumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.spring.kafka.shareconsumer.consumer.EventShareConsumer;

@Configuration
public class DualConsumersConfiguration {

    @Bean("eventShareConsumer1")
    public EventShareConsumer eventShareConsumer1() {
        return new EventShareConsumer();
    }

    @Bean("eventShareConsumer2")
    public EventShareConsumer eventShareConsumer2() {
        return new EventShareConsumer();
    }

}
