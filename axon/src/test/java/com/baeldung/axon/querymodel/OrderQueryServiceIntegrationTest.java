package com.baeldung.axon.querymodel;

import com.baeldung.axon.OrderApplication;
import com.baeldung.axon.coreapi.events.OrderConfirmedEvent;
import com.baeldung.axon.coreapi.events.OrderShippedEvent;
import com.baeldung.axon.coreapi.events.ProductAddedEvent;
import com.baeldung.axon.coreapi.events.ProductCountDecrementedEvent;
import com.baeldung.axon.coreapi.events.ProductCountIncrementedEvent;
import com.baeldung.axon.coreapi.queries.Order;

import org.axonframework.eventhandling.gateway.EventGateway;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = OrderApplication.class)
class OrderQueryServiceIntegrationTest {

    @Autowired
    OrderQueryService queryService;

    @Autowired
    EventGateway eventGateway;

    @Autowired
    OrdersEventHandler handler;

    private String orderId;
    private final String productId = "Deluxe Chair";

    @BeforeEach
    void setUp() {
        orderId = UUID.randomUUID()
          .toString();
        Order order = new Order(orderId);
        handler.reset(Collections.singletonList(order));
    }

    @Test
    void givenOrderCreatedEventSend_whenCallingAllOrders_thenOneCreatedOrderIsReturned() throws ExecutionException, InterruptedException {
        List<OrderResponse> result = queryService.findAllOrders()
          .get();
        assertEquals(1, result.size());
        OrderResponse response = result.get(0);
        assertEquals(orderId, response.getOrderId());
        assertEquals(OrderStatusResponse.CREATED, response.getOrderStatus());
        assertTrue(response.getProducts()
          .isEmpty());
    }

    @Test
    void givenOrderCreatedEventSend_whenCallingAllOrdersStreaming_thenOneOrderIsReturned() {
        Flux<OrderResponse> result = queryService.allOrdersStreaming();
        StepVerifier.create(result)
          .assertNext(order -> assertEquals(orderId, order.getOrderId()))
          .expectComplete()
          .verify();
    }

    @Test
    void givenThreeDeluxeChairsShipped_whenCallingAllShippedChairs_then234PlusTreeIsReturned() {
        Order order = new Order(orderId);
        order.getProducts()
          .put(productId, 3);
        order.setOrderShipped();
        handler.reset(Collections.singletonList(order));

        assertEquals(237, queryService.totalShipped(productId));
    }

    @Test
    void givenOrdersAreUpdated_whenCallingOrderUpdates_thenUpdatesReturned() {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(this::addIncrementDecrementConfirmAndShip, 100L, TimeUnit.MILLISECONDS);
        try {
            StepVerifier.create(queryService.orderUpdates(orderId))
              .assertNext(order -> assertTrue(order.getProducts()
                .isEmpty()))
              .assertNext(order -> assertEquals(1, order.getProducts()
                .get(productId)))
              .assertNext(order -> assertEquals(2, order.getProducts()
                .get(productId)))
              .assertNext(order -> assertEquals(1, order.getProducts()
                .get(productId)))
              .assertNext(order -> assertEquals(OrderStatusResponse.CONFIRMED, order.getOrderStatus()))
              .assertNext(order -> assertEquals(OrderStatusResponse.SHIPPED, order.getOrderStatus()))
              .thenCancel()
              .verify();
        } finally {
            executor.shutdown();
        }
    }

    private void addIncrementDecrementConfirmAndShip() {
        sendProductAddedEvent();
        sendProductCountIncrementEvent();
        sendProductCountDecrementEvent();
        sendOrderConfirmedEvent();
        sendOrderShippedEvent();
    }

    private void sendProductAddedEvent() {
        ProductAddedEvent event = new ProductAddedEvent(orderId, productId);
        eventGateway.publish(event);
    }

    private void sendProductCountIncrementEvent() {
        ProductCountIncrementedEvent event = new ProductCountIncrementedEvent(orderId, productId);
        eventGateway.publish(event);
    }

    private void sendProductCountDecrementEvent() {
        ProductCountDecrementedEvent event = new ProductCountDecrementedEvent(orderId, productId);
        eventGateway.publish(event);
    }

    private void sendOrderConfirmedEvent() {
        OrderConfirmedEvent event = new OrderConfirmedEvent(orderId);
        eventGateway.publish(event);
    }

    private void sendOrderShippedEvent() {
        OrderShippedEvent event = new OrderShippedEvent(orderId);
        eventGateway.publish(event);
    }
}
