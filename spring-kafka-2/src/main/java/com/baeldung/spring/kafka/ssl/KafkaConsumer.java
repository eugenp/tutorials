package com.baeldung.spring.kafka.ssl;

import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KafkaConsumer {

    public static final String TOPIC = "test-topic";

    public final List<String> messages = new ArrayList<>();

    @KafkaListener(topics = TOPIC)
    public void receive(ConsumerRecord<String, String> consumerRecord) {
        log.info("Received payload: '{}'", consumerRecord.toString());
        messages.add(consumerRecord.value());
    }

}
