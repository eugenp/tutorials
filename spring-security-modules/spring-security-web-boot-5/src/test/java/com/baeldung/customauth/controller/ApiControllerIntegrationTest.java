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
class ApiControllerIntegrationTest {

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    void givenApiCalledWithValidAuthHeader_whenApiControllerCalled_thenReturnHello() throws Exception {
        URI uri = new URI("http://localhost:8080/app/api/hello");
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-auth-secret-key", "test-secret");

        ResponseEntity<String> response = restTemplate
                .exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("hello", response.getBody());
    }

    @Test
    void givenApiCalledWithInValidAuthHeader_whenApiControllerCalled_thenReturnUnAuthorised() throws Exception {
        URI uri = new URI("http://localhost:8080/app/api/hello");
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-auth-secret-key", "invalid");

        ResponseEntity<String> response = restTemplate
                .exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), String.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void givenApiCalledWithoutAnyAuthHeader_whenApiControllerCalled_thenReturnUnAuthorised() throws Exception {
        URI uri = new URI("http://localhost:8080/app/api/hello");

        ResponseEntity<String> response = restTemplate
                .exchange(uri, HttpMethod.GET, null, String.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}
