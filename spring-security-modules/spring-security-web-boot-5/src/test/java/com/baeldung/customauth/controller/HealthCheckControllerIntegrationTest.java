package com.baeldung.customauth.controller;

import com.baeldung.customauth.SpringSecurityApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = SpringSecurityApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles({"test"})
@ExtendWith(SpringExtension.class)
class HealthCheckControllerIntegrationTest {

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    void givenApplicationIsRunning_whenHealthCheckControllerCalled_thenReturnOk() throws Exception {
        URI uri = new URI("http://localhost:8080/app/health");
        ResponseEntity<String> response = restTemplate
                .exchange(uri, HttpMethod.GET, null, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("OK", response.getBody());
    }
}
