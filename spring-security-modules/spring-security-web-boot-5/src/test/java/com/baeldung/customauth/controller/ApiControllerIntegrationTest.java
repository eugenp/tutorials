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
class ApiControllerIntegrationTest {

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    private static final String API_ENDPOINT = "http://localhost:%s/app/api/hello";

    @LocalServerPort
    private int serverPort;

    @Test
    void givenAuthHeaderSecretIsValid_whenApiControllerCalled_thenReturnOk() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-auth-secret-key", "test-secret");

        ResponseEntity<String> response = restTemplate.exchange(new URI(String.format(API_ENDPOINT, serverPort)), HttpMethod.GET,
          new HttpEntity<>(headers), String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("hello", response.getBody());
    }

    @Test
    void givenAAuthHeaderIsInvalid_whenApiControllerCalled_thenReturnUnAuthorised() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-auth-secret-key", "invalid");

        ResponseEntity<String> response = restTemplate.exchange(new URI(String.format(API_ENDPOINT, serverPort)), HttpMethod.GET,
          new HttpEntity<>(headers), String.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void givenAuthHeaderNameIsInValid_whenApiControllerCalled_thenReturnUnAuthorised() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-auth-secret", "test-secret");

        ResponseEntity<String> response = restTemplate.exchange(new URI(String.format(API_ENDPOINT, serverPort)), HttpMethod.GET,
          new HttpEntity<>(headers), String.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void givenAuthHeaderIsMissing_whenApiControllerCalled_thenReturnUnAuthorised() throws Exception {
        HttpHeaders headers = new HttpHeaders();

        ResponseEntity<String> response = restTemplate.exchange(new URI(String.format(API_ENDPOINT, serverPort)), HttpMethod.GET,
          new HttpEntity<>(headers), String.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}
