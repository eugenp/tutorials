package com.baeldung.concurrent;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.TimeUnit;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Request processor")
public class RequestProcessorUnitTest {

    RequestProcessor requestProcessor = new RequestProcessor();

    @Test
    @DisplayName("Wait for completion using Thread.sleep")
    void whenWaitingWithThreadSleep_thenStatusIsDone() throws InterruptedException {
        String requestId = requestProcessor.processRequest();

        Thread.sleep(2000);

        assertEquals("DONE", requestProcessor.getStatus(requestId));
    }

    @Test
    @DisplayName("Wait for completion using Awaitility")
    void whenWaitingWithAwaitility_thenStatusIsDone() {
        String requestId = requestProcessor.processRequest();

        Awaitility.await()
          .atMost(2, TimeUnit.SECONDS)
          .pollDelay(500, TimeUnit.MILLISECONDS)
          .until(() -> requestProcessor.getStatus(requestId), not(equalTo("PROCESSING")));

        assertEquals("DONE", requestProcessor.getStatus(requestId));
    }

}
