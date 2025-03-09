package com.baeldung.fallback.messaging;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("production")
@Primary
public class ProductionMessagingService implements MessagingService {

    @Override
    public void sendMessage(String text) {
        // implementation in production environment
    }
}