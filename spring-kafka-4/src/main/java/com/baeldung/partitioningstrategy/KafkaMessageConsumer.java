package com.baeldung.partitioningstrategy;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.kafka.support.KafkaHeaders;

import jakarta.annotation.Nullable;

@Service
public class KafkaMessageConsumer {
    private final List<ReceivedMessage> receivedMessages = new CopyOnWriteArrayList<>();

    @KafkaListener(topics = { "order-topic", "default-topic" }, groupId = "test-group")
    public void listen(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION) int partition, @Header(KafkaHeaders.RECEIVED_KEY) @Nullable String key) {
        ReceivedMessage receivedMessage = new ReceivedMessage(key, message, partition);
        System.out.println("Received message: " + receivedMessage);
        receivedMessages.add(receivedMessage);
    }

    public List<ReceivedMessage> getReceivedMessages() {
        return receivedMessages;
    }

    public void clearReceivedMessages() {
        receivedMessages.clear();
    }
}
