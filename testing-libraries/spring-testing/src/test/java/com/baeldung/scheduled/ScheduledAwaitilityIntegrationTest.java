package com.baeldung.scheduled;

import com.baeldung.config.ScheduledConfig;
import org.awaitility.Duration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@SpringJUnitConfig(ScheduledConfig.class)
public class ScheduledAwaitilityIntegrationTest {

    @SpyBean private Counter counter;

    @Test
    public void whenWaitOneSecond_thenScheduledIsCalledAtLeastTenTimes() {
        await()
          .atMost(Duration.ONE_SECOND)
          .untilAsserted(() -> verify(counter, atLeast(10)).scheduled());
    }
}
