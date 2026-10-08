package com.baeldung.spring.kafka.shareconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.baeldung.spring.kafka.shareconsumer.consumer.EventShareConsumer;

@EnableKafka
@SpringBootApplication
@EnableScheduling
public class ShareConsumerApp {

    public static void main(String[] args) {
        SpringApplication.run(ShareConsumerApp.class, args);
    }

    @Bean
    public EventShareConsumer eventShareConsumer() {
        return new EventShareConsumer();
    }
}
