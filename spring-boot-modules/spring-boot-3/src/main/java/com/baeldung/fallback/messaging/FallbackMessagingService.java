package com.baeldung.fallback.messaging;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Fallback;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Fallback
public class FallbackMessagingService implements IMessagingService {

    @Override
    public void sendMessage(String text) {
        // fallback implementation
    }
}