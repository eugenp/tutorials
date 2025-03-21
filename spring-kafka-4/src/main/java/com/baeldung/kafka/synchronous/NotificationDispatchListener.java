package com.baeldung.kafka.synchronous;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
class NotificationDispatchListener {

    @SendTo
    @KafkaListener(topics = "${com.baeldung.kafka.synchronous.request-topic}")
    NotificationDispatchResponse listen(NotificationDispatchRequest notificationDispatchRequest) {
        // ... processing logic
        UUID notificationId = UUID.randomUUID();
        return new NotificationDispatchResponse(notificationId);
    }

}