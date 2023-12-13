package com.baeldung.spring.cloud.aws.sqs;

import static io.awspring.cloud.sqs.listener.SqsHeaders.MessageSystemAttributes.SQS_APPROXIMATE_FIRST_RECEIVE_TIMESTAMP;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import io.awspring.cloud.sqs.annotation.SqsListener;

@Component
public class UserEventListeners {

    private static final Logger logger = LoggerFactory.getLogger(UserEventListeners.class);

    public static final String EVENT_TYPE_CUSTOM_HEADER = "custom-header";

    private final UserRepository userRepository;

    public UserEventListeners(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @SqsListener("${events.queues.user-created-by-name-queue}")
    public void receiveStringMessage(String username) {
        logger.info("Received message: {}", username);
        userRepository.save(new User(UUID.randomUUID()
            .toString(), username, null));
    }

    @SqsListener("${events.queues.user-created-fifo-queue}")
    public void receiveRecordMessage(UserCreatedEvent event) {
        logger.info("Received message: {}", event);
        userRepository.save(new User(event.id(), event.username(), event.email()));
    }

    @SqsListener("${events.queues.user-created-event-type-queue}")
    public void customHeaderMessage(UserCreatedEvent event, @Header(EVENT_TYPE_CUSTOM_HEADER) String eventType,
        @Header(SQS_APPROXIMATE_FIRST_RECEIVE_TIMESTAMP) Long firstReceive) {
        logger.info("Received message {} with event type {}. First received at approximately {}.", event, eventType, firstReceive);
        userRepository.save(new User(event.id(), event.username(), event.email()));
    }

}
