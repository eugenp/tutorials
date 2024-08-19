package com.baeldung.fallback.messaging;

import org.springframework.context.annotation.Fallback;
import org.springframework.stereotype.Service;

@Service
@Fallback
public class FallbackMessagingService implements MessagingService {

    @Override
    public void sendMessage(String text) {
        // fallback implementation
    }
}