package com.baeldung.kafka.streams;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Component
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        kafkaTemplate.send("input-topic", message)
            .whenComplete((result, ex) -> {
                if (ex == null) {
                    log.info("Message sent to topic: {}", message);
                } else {
                    log.error("Failed to send message", ex);
                }
            });
    }
}
