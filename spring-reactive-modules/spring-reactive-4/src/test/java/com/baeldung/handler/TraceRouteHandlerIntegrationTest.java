package com.baeldung.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.baeldung.router.TraceRouter;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TraceRouter.class, TraceRouterHandler.class })
@WebFluxTest
public class TraceRouteHandlerIntegrationTest {

    @Autowired
    private ApplicationContext context;
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToApplicationContext(context)
            .build();
    }

    @Test
    void whenCallTraceFunctionalFilterEndpoint_thenResponseContainsTraceId() {
        EntityExchangeResult<String> result = webTestClient.get()
            .uri("/trace-functional-filter")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(String.class)
            .returnResult();

        final String body = "TraceId: FUNCTIONAL-TRACE-ID";
        assertEquals(result.getResponseBody(), body);
    }

    @Test
    void whenCallTraceFunctionalBeforeEndpoint_thenResponseContainsTraceId() {
        EntityExchangeResult<String> result = webTestClient.get()
            .uri("/trace-functional-before")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(String.class)
            .returnResult();

        final String body = "TraceId: FUNCTIONAL-TRACE-ID";
        assertEquals(result.getResponseBody(), body);
    }
}
