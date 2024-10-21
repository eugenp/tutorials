package com.baeldung.spring.cloud.aws.sqs.acknowledgement;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.Objects;
import java.util.UUID;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.baeldung.spring.cloud.aws.sqs.acknowledgement.configuration.EventsQueuesProperties;
import com.baeldung.spring.cloud.aws.sqs.acknowledgement.configuration.ProductIdProperties;
import com.baeldung.spring.cloud.aws.sqs.acknowledgement.model.OrderCreatedEvent;
import com.baeldung.spring.cloud.aws.sqs.acknowledgement.model.OrderStatus;
import com.baeldung.spring.cloud.aws.sqs.acknowledgement.service.OrderService;

import io.awspring.cloud.sqs.listener.MessageListenerContainerRegistry;
import io.awspring.cloud.sqs.operations.SqsTemplate;

@ActiveProfiles("acknowledgement")
@SpringBootTest
class OrderProcessingApplicationLiveTest {

    private static final Logger logger = LoggerFactory.getLogger(OrderProcessingApplicationLiveTest.class);

    @Autowired
    private EventsQueuesProperties eventsQueuesProperties;

    @Autowired
    private ProductIdProperties productIdProperties;

    @Autowired
    private SqsTemplate sqsTemplate;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MessageListenerContainerRegistry registry;

    @Test
    public void givenOnSuccessAcknowledgementMode_whenProcessingThrows_shouldRetry() {
        var orderId = UUID.randomUUID();
        var queueName = eventsQueuesProperties.getOrderProcessingRetryQueue();
        sqsTemplate.send(queueName, new OrderCreatedEvent(orderId, productIdProperties.getLaptop(), 10));
        Awaitility.await()
            .atMost(Duration.ofMinutes(1))
            .until(() -> orderService.getOrderStatus(orderId)
                .equals(OrderStatus.PROCESSED));
        assertQueueIsEmpty(queueName, "retry-order-processing-container");
    }

    @Test
    public void givenManualAcknowledgementMode_whenManuallyAcknowledge_shouldAcknowledge() {
        var orderId = UUID.randomUUID();
        var queueName = eventsQueuesProperties.getOrderProcessingAsyncQueue();
        sqsTemplate.send(queueName, new OrderCreatedEvent(orderId, productIdProperties.getSmartphone(), 1));
        Awaitility.await()
            .atMost(Duration.ofMinutes(1))
            .until(() -> orderService.getOrderStatus(orderId)
                .equals(OrderStatus.PROCESSED));
        assertQueueIsEmpty(queueName, "async-order-processing-container");
    }

    @Test
    public void givenAlwaysAcknowledgementMode_whenProcessThrows_shouldAcknowledge() {
        var orderId = UUID.randomUUID();
        var queueName = eventsQueuesProperties.getOrderProcessingNoRetriesQueue();
        sqsTemplate.send(queueName, new OrderCreatedEvent(orderId, productIdProperties.getWirelessHeadphones(), 20));
        Awaitility.await()
            .atMost(Duration.ofMinutes(1))
            .until(() -> orderService.getOrderStatus(orderId)
                .equals(OrderStatus.RECEIVED));
        assertQueueIsEmpty(queueName, "no-retries-order-processing-container");
    }

    private void assertQueueIsEmpty(String queueName, String containerId) {
        // Stop the listener so it doesn't pick the message again if it's still there
        logger.info("Stopping container {}", containerId);
        var container = Objects
            .requireNonNull(registry.getContainerById(containerId), () -> "could not find container " + containerId);
        container.stop();
        // Look for messages in the queue
        logger.info("Checking for messages in queue {}", queueName);
        var message = sqsTemplate.receive(from -> from.queue(queueName)
            // Polltimeout here must be set to a higher value than the message visibility set in the annotation
            .pollTimeout(Duration.ofSeconds(5)));
        assertThat(message).isEmpty();
        logger.info("No messages found in queue {}", queueName);
    }

}
