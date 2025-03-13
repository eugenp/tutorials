package com.baeldung.fallback.messaging;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("!test")
public class DevelopmentMessagingService implements MessagingService {

    @Override
    public void sendMessage(String text) {
        // implementation in development environment
    }
}