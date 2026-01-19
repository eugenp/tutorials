package com.baeldung.retries.service;

import com.baeldung.retries.RetrylogicApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = RetrylogicApplication.class)
class RestClientServiceTest {

    @Autowired
    private RestClientService restClientService;

    @Test
    void whenHostOffline_thenRetriesAndRecovers() {
        String offlineUrl = "http://localhost:9999/api/data";

        String result = restClientService.fetchData(offlineUrl);

        assertTrue(result.contains("Fallback response"));
        assertTrue(result.contains(offlineUrl));
    }
}

