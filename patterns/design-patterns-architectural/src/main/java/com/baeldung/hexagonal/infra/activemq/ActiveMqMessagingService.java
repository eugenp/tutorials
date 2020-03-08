package com.baeldung.hexagonal.infra.activemq;

import com.baeldung.hexagonal.domain.MessagingService;

public class ActiveMqMessagingService implements MessagingService {

    @Override
    public void publishMessage(Object message) {
        // rabbitMQ publish message implementation
    }

}
