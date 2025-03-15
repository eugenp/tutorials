package com.baeldung.wiremock;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.wiremock.spring.EnableWireMock;

@SpringBootTest(classes = SimpleWiremockTest.AppConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableWireMock
class SimpleWiremockTest {

    @Value("${wiremock.server.baseUrl}")
    private String wireMockUrl;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void givenWireMockStub_whenGetPing_thenReturnsPong() {
        stubFor(get("/ping").willReturn(ok("pong")));

        ResponseEntity<String> response = restTemplate.getForEntity(wireMockUrl + "/ping", String.class);

        Assertions.assertEquals("pong", response.getBody());
    }

    @Test
    void givenWireMockStub_whenGetGreeting_thenReturnsMockedJsonResponse() {
        String mockResponse = "{\"message\": \"Hello, Baeldung!\"}";
        stubFor(get("/api/greeting")
          .willReturn(okJson(mockResponse)));

        ResponseEntity<String> response = restTemplate.getForEntity(wireMockUrl + "/api/greeting", String.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(mockResponse, response.getBody());
    }

    @Test
    void givenWireMockStub_whenGetUnknownResource_thenReturnsNotFound() {
        stubFor(get("/api/unknown").willReturn(aResponse().withStatus(404)));

        ResponseEntity<String> response = restTemplate.getForEntity(wireMockUrl + "/api/unknown", String.class);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @SpringBootApplication
    static class AppConfiguration {}
}
