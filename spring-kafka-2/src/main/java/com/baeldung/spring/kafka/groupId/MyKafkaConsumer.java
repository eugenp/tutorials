package com.baeldung.spring.kafka.groupId;

import java.util.concurrent.CountDownLatch;

import org.apache.kafka.clients.consumer.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class MyKafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyKafkaConsumer.class);

    private CountDownLatch latch = new CountDownLatch(1);

    private String payload;

    @KafkaListener(topics = "${kafka.topic.name:test-topic}", clientIdPrefix = "neo", groupId = "${kafka.consumer.groupId:test-consumer-group}", concurrency = "4")
    public void receive(@Payload String payload, Consumer<String, String> consumer) {
        LOGGER.info("Consumer='{}' received payload='{}'", consumer.groupMetadata()
            .memberId(), payload);
        this.payload = payload;

        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public void resetLatch() {
        latch = new CountDownLatch(1);
    }

    public String getPayload() {
        return payload;
    }
}
