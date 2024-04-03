package com.baeldung.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = TraceController.class)
public class TraceControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void whenCallTraceAnnotatedEndpoint_thenResponseContainsTraceId() {
        EntityExchangeResult<String> result = webTestClient.get()
            .uri("/trace-annotated")
            .exchange()
            .expectStatus().isOk()
            .expectBody(String.class)
            .returnResult();

        final String body = "TraceId: ANNOTATED-TRACE-ID";
        assertEquals(result.getResponseBody(), body);

    }
}
