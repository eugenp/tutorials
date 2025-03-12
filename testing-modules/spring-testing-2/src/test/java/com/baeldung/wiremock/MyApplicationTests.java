package com.baeldung.wiremock;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.wiremock.spring.EnableWireMock;

@SpringBootTest(classes = MyApplicationTests.AppConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableWireMock
class MyApplicationTests {

    @Value("${wiremock.server.baseUrl}")
    private String wireMockUrl;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void returns_a_ping() {
        stubFor(get("/ping").willReturn(ok("pong")));

        ResponseEntity<String> response = restTemplate.getForEntity(wireMockUrl + "/ping", String.class);

        Assertions.assertEquals("pong", response.getBody());
    }

    @SpringBootApplication
    static class AppConfiguration {}
}
