package com.baeldung.spring.kafka.viewheaders;

import java.util.Map;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageConsumer {

    @KafkaListener(topics = { "my-topic" }, groupId = "my-consumer-group")
    public void listen(@Payload String message, @Headers Map<String, Object> headers) {
        System.out.println("Received message: " + message);
        System.out.println("Headers:");
        headers.forEach((key, value) -> System.out.println(key + ": " + value));

        String topicName = (String) headers.get(KafkaHeaders.TOPIC);
        System.out.println("Topic: " + topicName);
        int partitionID = (int) headers.get(KafkaHeaders.RECEIVED_PARTITION);
        System.out.println("Partition ID: " + partitionID);
    }

    @KafkaListener(topics = { "my-topic" }, groupId = "my-consumer-group")
    public void listen(@Payload String message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topicName,
        @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {
        System.out.println("Topic: " + topicName);
        System.out.println("Partition ID: " + partition);
    }
}