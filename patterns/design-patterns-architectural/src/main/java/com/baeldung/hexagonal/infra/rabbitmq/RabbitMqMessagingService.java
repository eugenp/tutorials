package com.baeldung.hexagonal.infra.rabbitmq;

import com.baeldung.hexagonal.domain.MessagingService;

public class RabbitMqMessagingService implements MessagingService {

    @Override
    public void publishMessage(Object message) {
        // rabbitMQ publish message implementation
    }

}
