package com.baeldung.partitioningstrategy;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String topic, String key, String message) {
        kafkaTemplate.send(topic, key, message);
    }

    public void send(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }
}