package com.baeldung.spring.kafka.topicsandpartitions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(value = {KafkaTopicConfig.class, KafkaProducerConfig.class, KafkaConsumerConfig.class})
public class ThermostatApplicationKafkaApp {
    public static void main(String[] args) {
        SpringApplication.run(ThermostatApplicationKafkaApp.class, args);
    }
}