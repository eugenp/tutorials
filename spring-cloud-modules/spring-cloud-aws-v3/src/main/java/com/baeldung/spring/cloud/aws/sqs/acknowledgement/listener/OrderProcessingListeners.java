package com.baeldung.spring.cloud.aws.sqs.acknowledgement.listener;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.baeldung.spring.cloud.aws.sqs.acknowledgement.model.OrderCreatedEvent;
import com.baeldung.spring.cloud.aws.sqs.acknowledgement.model.OrderStatus;
import com.baeldung.spring.cloud.aws.sqs.acknowledgement.service.OrderService;
import com.baeldung.spring.cloud.aws.sqs.acknowledgement.service.InventoryService;

import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.annotation.SqsListenerAcknowledgementMode;
import io.awspring.cloud.sqs.listener.acknowledgement.Acknowledgement;

@Component
public class OrderProcessingListeners {

    private static final Logger logger = LoggerFactory.getLogger(OrderProcessingListeners.class);

    private final InventoryService inventoryService;

    private final OrderService orderService;

    public OrderProcessingListeners(InventoryService inventoryService, OrderService orderService) {
        this.inventoryService = inventoryService;
        this.orderService = orderService;
    }

    @SqsListener(value = "${events.queues.order-processing-retry-queue}", id = "retry-order-processing-container", messageVisibilitySeconds = "1")
    public void stockCheckRetry(OrderCreatedEvent orderCreatedEvent) {
        logger.info("Message received: {}", orderCreatedEvent);

        orderService.updateOrderStatus(orderCreatedEvent.id(), OrderStatus.PROCESSING);
        inventoryService.checkInventory(orderCreatedEvent.productId(), orderCreatedEvent.quantity());
        orderService.updateOrderStatus(orderCreatedEvent.id(), OrderStatus.PROCESSED);
        logger.info("Message processed successfully: {}", orderCreatedEvent);
    }

    @SqsListener(value = "${events.queues.order-processing-async-queue}", acknowledgementMode = SqsListenerAcknowledgementMode.MANUAL, id = "async-order-processing-container", messageVisibilitySeconds = "3")
    public void slowStockCheckAsynchronous(OrderCreatedEvent orderCreatedEvent, Acknowledgement acknowledgement) {
        logger.info("Message received: {}", orderCreatedEvent);

        orderService.updateOrderStatus(orderCreatedEvent.id(), OrderStatus.PROCESSING);
        CompletableFuture.runAsync(() -> inventoryService.slowCheckInventory(orderCreatedEvent.productId(), orderCreatedEvent.quantity()))
            .thenRun(() -> orderService.updateOrderStatus(orderCreatedEvent.id(), OrderStatus.PROCESSED))
            .thenCompose(voidFuture -> acknowledgement.acknowledgeAsync())
            .thenRun(() -> logger.info("Message for order {} acknowledged", orderCreatedEvent.id()));

        logger.info("Releasing processing thread.");
    }

    @SqsListener(value = "${events.queues.order-processing-no-retries-queue}", acknowledgementMode = "${events.acknowledgment.order-processing-no-retries-queue}", id = "no-retries-order-processing-container", messageVisibilitySeconds = "3")
    public void stockCheckNoRetries(OrderCreatedEvent orderCreatedEvent) {
        logger.info("Message received: {}", orderCreatedEvent);

        // Fire and forget scenario where we're not  interested on the outcome, e.g. a sales event with limited inventory.
        orderService.updateOrderStatus(orderCreatedEvent.id(), OrderStatus.RECEIVED);
        inventoryService.checkInventory(orderCreatedEvent.productId(), orderCreatedEvent.quantity());
        logger.info("Message processed: {}", orderCreatedEvent);
    }

}
