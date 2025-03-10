package com.baeldung.fallback;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.fallback.messaging.DevelopmentMessagingService;
import com.baeldung.fallback.messaging.FallbackMessagingService;
import com.baeldung.fallback.messaging.MessagingService;
import com.baeldung.fallback.messaging.ProductionMessagingService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {FallbackMessagingService.class, DevelopmentMessagingService.class, ProductionMessagingService.class})
@ActiveProfiles("test")
public class FallbackMessagingServiceUnitTest {

    @Autowired
    private MessagingService messagingService;

    @Test
    public void givenTestProfile_whenSendMessage_thenFallbackMessagingService() {
        assertEquals(messagingService.getClass(), FallbackMessagingService.class);
    }
}