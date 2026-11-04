package com.baeldung.spring.kafka.shareconsumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

import com.baeldung.spring.kafka.shareconsumer.model.Event;

public class ThrowingEventShareConsumer {

    private static final Logger log = LoggerFactory.getLogger(ThrowingEventShareConsumer.class);

    @KafkaListener(topics = "stopic", groupId = "shgroup", containerFactory = "shareKafkaListenerContainerFactory")
    public void consume(ConsumerRecord<String, Event> record) {
        // Event skipping for kafka warmup
        if (record.value()
            .id() < 0L) {
            log.info("Skipping event {}", record.value());
            return;
        }
        // Simulating erroneous event consumption
        throw new RuntimeException("Something went wrong!");
    }

}
