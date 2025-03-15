package com.baeldung.kafka.synchronous;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
class NotificationDispatchListener {

    @KafkaListener(topics = "${com.baeldung.kafka.synchronous.request-topic}")
    @SendTo(value = "${com.baeldung.kafka.synchronous.reply-topic}")
    NotificationDispatchResponse listen(NotificationDispatchRequest notificationDispatchRequest) {
        // ... processing logic
        UUID notificationId = UUID.randomUUID();
        return new NotificationDispatchResponse(notificationId);
    }

}