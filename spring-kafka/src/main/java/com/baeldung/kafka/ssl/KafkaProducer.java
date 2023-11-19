package com.baeldung.kafka.ssl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message, String topic) {
        log.info("Producing message: {}", message);
        kafkaTemplate.send(topic, "key", message)
            .whenComplete((result, ex) -> {
                if (ex == null) {
                    log.info("Message sent to topic: {}", message);
                } else {
                    log.error("Failed to send message", ex);
                }
            });
    }
}
