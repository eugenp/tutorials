package com.baeldung.customauth.controller;

import com.baeldung.customauth.SpringSecurityApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = SpringSecurityApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
class HealthCheckControllerIntegrationTest {

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    private static final String HEALTH_CHECK_ENDPOINT = "http://localhost:%s/app/health";

    @LocalServerPort
    private int serverPort;

    @Test
    void givenApplicationIsRunning_whenHealthCheckControllerCalled_thenReturnOk() throws Exception {
        HttpHeaders headers = new HttpHeaders();

        ResponseEntity<String> response = restTemplate.exchange(new URI(String.format(HEALTH_CHECK_ENDPOINT, serverPort)), HttpMethod.GET,
          new HttpEntity<>(headers), String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("OK", response.getBody());
    }
}
