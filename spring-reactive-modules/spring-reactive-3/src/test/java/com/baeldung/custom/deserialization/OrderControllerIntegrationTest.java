package com.baeldung.custom.deserialization;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.baeldung.custom.deserialization.model.OrderResponse;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(properties = "server.port=8091")
@AutoConfigureWebTestClient(timeout = "100000")
class OrderControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    private static MockWebServer mockExternalService;

    @BeforeAll
    static void setup() throws IOException {
        mockExternalService = new MockWebServer();
        mockExternalService.start(8090);
    }

    @Test
    void givenMockedExternalResponse_whenSearchByIdV1_thenOrderResponseShouldFailBecauseOfUnknownProperty() {

        mockExternalService.enqueue(new MockResponse().addHeader("Content-Type", "application/json; charset=utf-8")
            .setBody("{\n" + "                      \"orderId\": \"a1b2c3d4-e5f6-4a5b-8c9d-0123456789ab\",\n" +
                "                      \"orderDateTime\": \"2024-01-20T12:34:56\",\n" +
                "                      \"address\": [\"123 Main St\", \"Apt 456\", \"Cityville\"],\n" +
                "                      \"orderNotes\": [\"Special request: Handle with care\", \"Gift wrapping required\"],\n" +
                "                      \"customerName\": \"John Doe\",\n" + "                      \"totalAmount\": 99.99,\n" +
                "                      \"paymentMethod\": \"Credit Card\"\n" + "                    }")
            .setResponseCode(HttpStatus.OK.value()));

        webTestClient.get()
            .uri("v1/order/1")
            .exchange()
            .expectStatus()
            .is5xxServerError();
    }

    @Test
    void givenMockedExternalResponse_whenSearchByIdV1_thenOrderResponseShouldBeReceivedSuccessfully() {

        mockExternalService.enqueue(new MockResponse().addHeader("Content-Type", "application/json; charset=utf-8")
            .setBody("{\n" + "                  \"orderId\": \"a1b2c3d4-e5f6-4a5b-8c9d-0123456789ab\",\n" +
                "                  \"orderDateTime\": \"2024-01-20T12:34:56\",\n" +
                "                  \"address\": [\"123 Main St\", \"Apt 456\", \"Cityville\"],\n" +
                "                  \"orderNotes\": [\"Special request: Handle with care\", \"Gift wrapping required\"]\n" + "                }")
            .setResponseCode(HttpStatus.OK.value()));

        OrderResponse orderResponse = webTestClient.get()
            .uri("v1/order/1")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(OrderResponse.class)
            .returnResult()
            .getResponseBody();
        assertEquals(UUID.fromString("a1b2c3d4-e5f6-4a5b-8c9d-0123456789ab"), orderResponse.getOrderId());
        assertEquals(LocalDateTime.of(2024, 1, 20, 12, 34, 56), orderResponse.getOrderDateTime());
        assertThat(orderResponse.getAddress()).hasSize(3);
        assertThat(orderResponse.getOrderNotes()).hasSize(2);
    }

    @Test
    void givenMockedExternalResponse_whenSearchByIdV2_thenOrderResponseShouldFailBecauseOfUnknownProperty() {

        mockExternalService.enqueue(new MockResponse().addHeader("Content-Type", "application/json; charset=utf-8")
            .setBody("{\n" + "                  \"orderId\": \"a1b2c3d4-e5f6-4a5b-8c9d-0123456789ab\",\n" +
                "                  \"orderDateTime\": \"2024-01-20T12:34:56\",\n" +
                "                  \"address\": [\"123 Main St\", \"Apt 456\", \"Cityville\"],\n" +
                "                  \"orderNotes\": [\"Special request: Handle with care\", \"Gift wrapping required\"],\n" +
                "                  \"customerName\": \"John Doe\",\n" + "                  \"totalAmount\": 99.99,\n" +
                "                  \"paymentMethod\": \"Credit Card\"\n" + "                }")
            .setResponseCode(HttpStatus.OK.value()));

        webTestClient.get()
            .uri("v2/order/1")
            .exchange()
            .expectStatus()
            .is5xxServerError();
    }

    @Test
    void givenMockedExternalResponse_whenSearchByIdV2_thenOrderResponseShouldBeReceivedSuccessfully() {

        mockExternalService.enqueue(new MockResponse().addHeader("Content-Type", "application/json; charset=utf-8")
            .setBody("{\n" + "                  \"orderId\": \"a1b2c3d4-e5f6-4a5b-8c9d-0123456789ab\",\n" +
                "                  \"orderDateTime\": \"2024-01-20T14:34:56+01:00\",\n" +
                "                  \"address\": [\"123 Main St\", \"Apt 456\", \"Cityville\"],\n" +
                "                  \"orderNotes\": [\"Special request: Handle with care\", \"Gift wrapping required\"]\n" + "                }")
            .setResponseCode(HttpStatus.OK.value()));

        OrderResponse orderResponse = webTestClient.get()
            .uri("v2/order/1")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(OrderResponse.class)
            .returnResult()
            .getResponseBody();
        assertEquals(UUID.fromString("a1b2c3d4-e5f6-4a5b-8c9d-0123456789ab"), orderResponse.getOrderId());
        assertEquals(LocalDateTime.of(2024, 1, 20, 13, 34, 56), orderResponse.getOrderDateTime());
        assertThat(orderResponse.getAddress()).hasSize(3);
        assertThat(orderResponse.getOrderNotes()).hasSize(2);
    }

    @Test
    void givenMockedExternalResponse_whenSearchByMultipleAddress_thenAddressShouldBeReceivedSuccessfully() {

        mockExternalService.enqueue(new MockResponse().addHeader("Content-Type", "application/json; charset=utf-8")
            .setBody("[\"123 Main St\", \"456 Oak Ave\", \"789 Pine Rd\"]")
            .setResponseCode(HttpStatus.OK.value()));

        List<String> address = webTestClient.get()
            .uri(uriBuilder -> uriBuilder.path("/v3/order")
                .queryParam("address", "123 Main St", "456 Oak Ave", "789 Pine Rd")
                .build())
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(new ParameterizedTypeReference<List<String>>() {
            })
            .returnResult()
            .getResponseBody();
        assertThat(address).isNotNull();
        assertThat(address).hasSize(3);
        assertThat(address).containsExactly("123 Main St", "456 Oak Ave", "789 Pine Rd");
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockExternalService.shutdown();
    }

}