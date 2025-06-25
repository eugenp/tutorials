package com.baeldung.sasl;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class KafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);
    public static final String TOPIC = "test-topic";
    public final List<String> messages = new ArrayList<>();

    @KafkaListener(topics = TOPIC)
    public void receive(ConsumerRecord<String, String> consumerRecord) {
        LOGGER.info("Received payload: '{}'", consumerRecord.toString());
        messages.add(consumerRecord.value());
    }
}
