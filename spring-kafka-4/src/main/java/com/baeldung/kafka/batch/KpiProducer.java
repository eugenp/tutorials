package com.baeldung.kafka.batch;

import java.util.concurrent.ExecutionException;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KpiProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KpiProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String message) throws ExecutionException, InterruptedException {
        kafkaTemplate.send(topic, message).get();
        this.kafkaTemplate.flush();
    }
}
