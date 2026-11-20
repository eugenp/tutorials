package com.baeldung.springretry;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpringRetryListenerIntegrationTest {

    @Autowired
    private ExternalService externalService;

    @Test
    void givenFailingExternalService_whenCallExternalApi_thenShouldRetryMultipleTimesAndLogRetriesExhausted() {
        assertThrows(RuntimeException.class, () -> {
            externalService.callExternalApi();
        });
    }

}
