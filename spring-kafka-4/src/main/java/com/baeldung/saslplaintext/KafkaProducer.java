package com.baeldung.saslplaintext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message, String topic) {
        LOGGER.info("Producing message: {}", message);
        kafkaTemplate.send(topic, "key", message)
            .whenComplete((result, ex) -> {
                if (ex == null) {
                    LOGGER.info("Message sent to topic: {}", message);
                } else {
                    LOGGER.error("Failed to send message", ex);
                }
            });
    }
}
