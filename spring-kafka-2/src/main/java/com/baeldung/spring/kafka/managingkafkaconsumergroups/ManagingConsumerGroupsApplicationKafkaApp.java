package com.baeldung.spring.kafka.managingkafkaconsumergroups;

import com.baeldung.spring.kafka.topicsandpartitions.KafkaConsumerConfig;
import com.baeldung.spring.kafka.topicsandpartitions.KafkaProducerConfig;
import com.baeldung.spring.kafka.topicsandpartitions.KafkaTopicConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(value = {KafkaTopicConfig.class, KafkaProducerConfig.class, KafkaConsumerConfig.class})
public class ManagingConsumerGroupsApplicationKafkaApp {
    public static void main(String[] args) {
        SpringApplication.run(ManagingConsumerGroupsApplicationKafkaApp.class, args);
    }
}