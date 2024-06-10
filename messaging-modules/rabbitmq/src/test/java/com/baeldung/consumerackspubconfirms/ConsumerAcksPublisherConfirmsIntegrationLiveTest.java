package com.baeldung.consumerackspubconfirms;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ConsumerAcksPublisherConfirmsIntegrationLiveTest {

    private static final String QUEUE = "uuid-validation";
    private static final int SINGLE_CONFIRM_MESSAGES = 2;
    private static final int BATCH_CONFIRM_MESSAGES = 6;

    private static ConnectionFactory connectionFactory;
    private Connection connection;
    private Channel channel;

    @BeforeAll
    static void init() {
        connectionFactory = new ConnectionFactory();
    }

    @BeforeEach
    void setUp() throws IOException, TimeoutException {
        this.connection = connectionFactory.newConnection();
        this.channel = connection.createChannel();
        this.channel.queueDeclare(QUEUE, false, false, false, null);
    }

    @AfterEach
    void tearDown() throws IOException, TimeoutException {
        if (channel != null) {
            channel.abort();
            channel = null;
        }

        if (connection != null) {
            connection.abort();
            connection = null;
        }
    }

    private List<String> generateRandomUuids(int n, int invalidIndex) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (i == invalidIndex) {
                list.add("invalid-uuid-" + i);
            } else {
                list.add(UUID.randomUUID()
                    .toString());
            }
        }
        return list;
    }

    @Test
    @Order(1)
    void whenPublishingWithSingleConfirms_thenEachMessageConfirmedByBroker() throws IOException, InterruptedException, TimeoutException {
        UuidPublisher publisher = new UuidPublisher(channel, QUEUE);

        for (int i = 0; i < SINGLE_CONFIRM_MESSAGES; i++) {
            String message = UUID.randomUUID()
                .toString();
            assertTrue(publisher.send(message));
        }
    }

    @Test
    @Order(2)
    void whenConsumerWithBatchAcks_thenExpectedMessagesConsumed() throws Exception {
        int batchSize = SINGLE_CONFIRM_MESSAGES;

        CountDownLatch latch = new CountDownLatch(batchSize);
        try (UuidConsumer consumer = new UuidConsumer(channel, QUEUE, batchSize)) {
            consumer.consume(latch);

            boolean expectedMessagesConsumed = latch.await(8, TimeUnit.SECONDS);
            assertTrue(expectedMessagesConsumed);
        }
    }

    @Test
    @Order(3)
    void whenPublishingWithBatchConfirms_thenMultipleMessagesConfirmedByBroker() throws IOException, InterruptedException, TimeoutException {
        List<String> messages = generateRandomUuids(BATCH_CONFIRM_MESSAGES, 2);

        UuidPublisher publisher = new UuidPublisher(channel, QUEUE);
        assertDoesNotThrow(() -> publisher.sendAllOrDie(messages));
    }

    @Test
    @Order(4)
    void whenPublishingWithConfirmListener_thenMessagesConfirmedAsynchronously() throws IOException, InterruptedException, TimeoutException {
        List<String> messages = generateRandomUuids(BATCH_CONFIRM_MESSAGES, 2);
        CountDownLatch latch = new CountDownLatch(1);

        UuidPublisher publisher = new UuidPublisher(channel, QUEUE);
        publisher.sendOrRetry(messages, latch);

        boolean allMessagesSentOrRetried = latch.await(5, TimeUnit.SECONDS);
        System.out.println("-> publisher latch: " + latch.getCount());
        assertTrue(allMessagesSentOrRetried);
    }

    @Test
    @Order(5)
    void whenConsumerWithAcks_thenLimitedMessagesAcked() throws Exception {
        int messages = BATCH_CONFIRM_MESSAGES * 2;
        int batchSize = BATCH_CONFIRM_MESSAGES / 2;

        CountDownLatch latch = new CountDownLatch(messages);
        try (UuidConsumer consumer = new UuidConsumer(channel, QUEUE, batchSize)) {
            consumer.consume(latch);

            boolean expectedMessagesConsumed = latch.await(8, TimeUnit.SECONDS);
            assertTrue(expectedMessagesConsumed);
        }
    }
}
