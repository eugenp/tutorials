package com.baeldung.spring.kafka.managingkafkaconsumergroups;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(value = {KafkaConsumerConfiguration.class, KafkaProducerConfiguration.class, KafkaTopicConfiguration.class})
public class ManagingConsumerGroupsApplicationKafkaApp {
    public static void main(String[] args) {
        SpringApplication.run(ManagingConsumerGroupsApplicationKafkaApp.class, args);
    }
}