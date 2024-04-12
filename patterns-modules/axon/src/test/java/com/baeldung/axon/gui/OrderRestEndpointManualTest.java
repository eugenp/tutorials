package com.baeldung.axon.gui;

import com.baeldung.axon.OrderApplication;
import com.baeldung.axon.querymodel.OrderResponse;
import com.baeldung.axon.querymodel.OrderStatusResponse;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = OrderApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//marked as manual as the test is unstable on Jenkins due to low resources
class OrderRestEndpointManualTest {

    @LocalServerPort
    private int port;

    @Test
    @DirtiesContext
    void givenCreateOrderCalled_whenCallingAllOrders_thenOneCreatedOrderIsReturned() {
        WebClient client = WebClient.builder()
          .clientConnector(httpConnector())
          .build();
        createRandomNewOrder(client);
        StepVerifier.create(retrieveListResponse(client.get()
            .uri("http://localhost:" + port + "/all-orders")))
          .expectNextMatches(list -> 1 == list.size() && list.get(0)
            .getOrderStatus() == OrderStatusResponse.CREATED)
          .verifyComplete();
    }

    @Test
    @DirtiesContext
    void givenCreateOrderCalledThreeTimesAnd_whenCallingAllOrdersStreaming_thenTwoCreatedOrdersAreReturned() {
        WebClient client = WebClient.builder()
          .clientConnector(httpConnector())
          .build();
        for (int i = 0; i < 3; i++) {
            createRandomNewOrder(client);
        }
        StepVerifier.create(retrieveStreamingResponse(client.get()
            .uri("http://localhost:" + port + "/all-orders-streaming")))
          .expectNextMatches(o -> o.getOrderStatus() == OrderStatusResponse.CREATED)
          .expectNextMatches(o -> o.getOrderStatus() == OrderStatusResponse.CREATED)
          .expectNextMatches(o -> o.getOrderStatus() == OrderStatusResponse.CREATED)
          .verifyComplete();
    }

    @Test
    @DirtiesContext
    void givenRuleExistThatNeedConfirmationBeforeShipping_whenCallingShipUnconfirmed_thenErrorReturned() {
        WebClient client = WebClient.builder()
          .clientConnector(httpConnector())
          .build();
        StepVerifier.create(retrieveResponse(client.post()
            .uri("http://localhost:" + port + "/ship-unconfirmed-order")))
          .verifyError(WebClientResponseException.class);
    }

    @Test
    @DirtiesContext
    void givenShipOrderCalled_whenCallingAllShippedChairs_then234PlusOneIsReturned() {
        WebClient client = WebClient.builder()
          .clientConnector(httpConnector())
          .build();
        verifyVoidPost(client, "http://localhost:" + port + "/ship-order");
        StepVerifier.create(retrieveIntegerResponse(client.get()
            .uri("http://localhost:" + port + "/total-shipped/Deluxe Chair")))
          .assertNext(r -> assertEquals(235, r))
          .verifyComplete();
    }

    @Test
    @DirtiesContext
    void givenOrdersAreUpdated_whenCallingOrderUpdates_thenUpdatesReturned() {
        WebClient updaterClient = WebClient.builder()
          .clientConnector(httpConnector())
          .build();
        WebClient receiverClient = WebClient.builder()
          .clientConnector(httpConnector())
          .build();
        String orderId = UUID.randomUUID()
          .toString();
        String productId = UUID.randomUUID()
          .toString();
        StepVerifier.create(retrieveResponse(updaterClient.post()
            .uri("http://localhost:" + port + "/order/" + orderId)))
          .assertNext(Assertions::assertNotNull)
          .verifyComplete();
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(() -> addIncrementDecrementConfirmAndShipProduct(orderId, productId), 1L, TimeUnit.SECONDS);
        try {
            StepVerifier.create(retrieveStreamingResponse(receiverClient.get()
                .uri("http://localhost:" + port + "/order-updates/" + orderId)))
              .assertNext(p -> assertTrue(p.getProducts()
                .isEmpty()))
              .assertNext(p -> assertEquals(1, p.getProducts()
                .get(productId)))
              .assertNext(p -> assertEquals(2, p.getProducts()
                .get(productId)))
              .assertNext(p -> assertEquals(1, p.getProducts()
                .get(productId)))
              .assertNext(p -> assertEquals(OrderStatusResponse.CONFIRMED, p.getOrderStatus()))
              .assertNext(p -> assertEquals(OrderStatusResponse.SHIPPED, p.getOrderStatus()))
              .thenCancel()
              .verify();
        } finally {
            executor.shutdown();
        }
    }

    private void addIncrementDecrementConfirmAndShipProduct(String orderId, String productId) {
        WebClient client = WebClient.builder()
          .clientConnector(httpConnector())
          .build();
        String base = "http://localhost:" + port + "/order/" + orderId;
        verifyVoidPost(client, base + "/product/" + productId);
        verifyVoidPost(client, base + "/product/" + productId + "/increment");
        verifyVoidPost(client, base + "/product/" + productId + "/decrement");
        verifyVoidPost(client, base + "/confirm");
        verifyVoidPost(client, base + "/ship");
    }

    private void createRandomNewOrder(WebClient client){
        StepVerifier.create(retrieveResponse(client.post()
            .uri("http://localhost:" + port + "/order")))
          .assertNext(Assertions::assertNotNull)
          .verifyComplete();
    }

    private void verifyVoidPost(WebClient client, String uri) {
        StepVerifier.create(retrieveResponse(client.post()
            .uri(uri)))
          .verifyComplete();
    }

    private static ReactorClientHttpConnector httpConnector() {
        HttpClient httpClient = HttpClient.create()
          .wiretap(true);
        return new ReactorClientHttpConnector(httpClient);
    }

    private Mono<String> retrieveResponse(WebClient.RequestBodySpec spec) {
        return spec.retrieve()
          .bodyToMono(String.class);
    }

    private Mono<ResponseList> retrieveListResponse(WebClient.RequestHeadersSpec<?> spec) {
        return spec.accept(MediaType.APPLICATION_JSON)
          .retrieve()
          .bodyToMono(ResponseList.class);
    }

    private Mono<Integer> retrieveIntegerResponse(WebClient.RequestHeadersSpec<?> spec) {
        return spec.retrieve()
          .bodyToMono(Integer.class);
    }

    private Flux<OrderResponse> retrieveStreamingResponse(WebClient.RequestHeadersSpec<?> spec) {
        return spec.retrieve()
          .bodyToFlux(OrderResponse.class);
    }

    private static class ResponseList extends ArrayList<OrderResponse> {

        private ResponseList() {
            super();
        }
    }
}
