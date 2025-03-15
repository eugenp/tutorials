package com.baeldung.kafka.synchronous;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
class NotificationDispatchListener {

    @KafkaListener(topics = "${com.baeldung.kafka.synchronous.request-topic}")
    @SendTo(value = "${com.baeldung.kafka.synchronous.reply-topic}")
    NotificationDispatchResponse listen(NotificationDispatchRequest notificationDispatchRequest) {
        // ... processing logic
        UUID notificationId = UUID.randomUUID();
        return new NotificationDispatchResponse(notificationId);
    }

}