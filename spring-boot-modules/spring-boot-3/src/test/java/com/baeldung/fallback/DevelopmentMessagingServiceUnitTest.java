package com.baeldung.fallback;

import com.baeldung.fallback.messaging.DevelopmentMessagingService;
import com.baeldung.fallback.messaging.FallbackMessagingService;
import com.baeldung.fallback.messaging.MessagingService;
import com.baeldung.fallback.messaging.ProductionMessagingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {FallbackMessagingService.class, DevelopmentMessagingService.class, ProductionMessagingService.class})
public class DevelopmentMessagingServiceUnitTest {

    @Autowired
    private MessagingService messagingService;

    @Test
    public void givenNoProfile_whenSendMessage_thenDevelopmentMessagingService() {
        assertEquals(messagingService.getClass(), DevelopmentMessagingService.class);
    }
}