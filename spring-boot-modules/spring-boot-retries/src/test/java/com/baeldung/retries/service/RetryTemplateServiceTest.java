package com.baeldung.retries.service;

import com.baeldung.retries.RetrylogicApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = {
        RetryTemplateService.class,
        RetrylogicApplication .class
})
class RetryTemplateServiceTest {

    @Autowired
    private RetryTemplateService retryTemplateService;

    @Test
    void whenHostOffline_thenReturnsFallback() {
        String offlineUrl = "http://localhost:9999/api/data";

        long startTime = System.currentTimeMillis();
        String result = retryTemplateService
                .fetchDataWithRetryTemplate(offlineUrl);
        long duration = System.currentTimeMillis() - startTime;

        assertEquals("Fallback response", result);
        assertTrue(duration >= 4000);
    }

}

