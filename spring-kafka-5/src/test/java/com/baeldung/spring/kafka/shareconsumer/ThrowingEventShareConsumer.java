package com.baeldung.spring.kafka.shareconsumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

import com.baeldung.spring.kafka.shareconsumer.model.Event;

public class ThrowingEventShareConsumer {

    @KafkaListener(topics = "stopic", groupId = "shgroup", containerFactory = "shareKafkaListenerContainerFactory")
    public void consume(ConsumerRecord<String, Event> record) {
        // Simulating erroneous event consumption
        throw new RuntimeException("Something went wrong!");
    }

}
