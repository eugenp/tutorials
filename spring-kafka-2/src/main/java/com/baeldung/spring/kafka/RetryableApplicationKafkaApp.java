package com.baeldung.spring.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(value = { KafkaTopicConfig.class, KafkaConsumerConfig.class, KafkaProducerConfig.class })
public class RetryableApplicationKafkaApp {

    public static void main(String[] args)  {
        SpringApplication.run(RetryableApplicationKafkaApp.class, args);
    }
}
