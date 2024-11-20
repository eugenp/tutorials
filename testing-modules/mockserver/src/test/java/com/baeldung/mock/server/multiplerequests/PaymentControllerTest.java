package com.baeldung.mock.server.multiplerequests;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.matchers.Times;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureMockMvc
class PaymentControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    private ClientAndServer clientAndServer;

    private final MockServerClient mockServerClient = new MockServerClient("localhost", 9090);

    @BeforeEach
    void setup() {
        clientAndServer = startClientAndServer(9090);
    }

    @Test
    void givenPaymentRequest_whenPaymentStatusChangesAfterFewSeconds_thenPaymentShouldBeProcessedSuccessfully() {

        String paymentId = UUID.randomUUID()
            .toString();
        // When
        mockServerClient.when(request().withMethod("POST")
                .withPath("/payment/submit"))
            .respond(response().withStatusCode(200)
                .withBody("{\"paymentId\": \"%s\"}".formatted(paymentId))
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));

        mockServerClient.when(request().withMethod("GET")
                .withPath("/payment/status/%s".formatted(paymentId)), Times.exactly(4))
            .respond(response().withStatusCode(200)
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withBody("{\"paymentStatus\": \"%s\"}".formatted(PaymentGatewayResponse.PaymentStatus.PENDING.toString())));

        mockServerClient.when(request().withMethod("GET")
                .withPath("/payment/status/%s".formatted(paymentId)))
            .respond(response().withStatusCode(200)
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withBody("{\"paymentStatus\": \"%s\"}".formatted(PaymentGatewayResponse.PaymentStatus.AUTHORIZED.toString())));

        webTestClient.post()
            .uri("http://localhost:9000/api/payment/process")
            .bodyValue(new PaymentGatewayRequest("4111111111111111", "12", "2025", "USD", 10000, "123"))
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(PaymentGatewayResponse.class)
            .value(response -> {
                Assertions.assertNotNull(response);
                Assertions.assertEquals(PaymentGatewayResponse.PaymentStatus.AUTHORIZED, response.status());
            });
    }

    @AfterEach
    void tearDown() {
        clientAndServer.stop();
    }

}