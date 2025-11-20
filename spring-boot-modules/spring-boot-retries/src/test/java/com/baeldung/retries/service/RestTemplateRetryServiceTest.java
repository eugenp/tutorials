package com.baeldung.retries.service;


import com.baeldung.retries.RetrylogicApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = RetrylogicApplication.class)
class RestTemplateRetryServiceTest {

    @Autowired
    private RestTemplateRetryService service;

    @Test
    void whenHostOffline_thenRetriesAndFails() {
        String offlineUrl = "http://localhost:9999/api/data";

        long startTime = System.currentTimeMillis();

        assertThrows(RuntimeException.class, () -> {
            service.makeRequestWithRetry(offlineUrl);
        });

        long duration = System.currentTimeMillis() - startTime;
        assertTrue(duration >= 4000);
    }

}

