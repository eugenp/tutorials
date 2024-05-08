package com.baeldung.consumerackspubconfirms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ConsumerAcksPublisherConfirmsIntegrationLiveTest {

    static final String QUEUE = ConsumerAcksPublisherConfirmsIntegrationLiveTest.class.getName();

    private List<String> generateRandomMessages(int n) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(UUID.randomUUID()
                .toString());
        }
        return list;
    }

    @Test
    @Order(1)
    void whenPublishingWithConfirms_thenMessagesConfirmed() throws IOException, InterruptedException, TimeoutException {
        try (StringPublisher publisher = new StringPublisher(QUEUE)) {
            assertTrue(publisher.send("hello"));
            assertTrue(publisher.send("world"));
        }
    }

    @Test
    @Order(2)
    void givenMultipleMessageLimit_whenConsumerWithAcks_thenAllMessagesAcked() throws IOException, TimeoutException, InterruptedException, ExecutionException {
        int maxMessages = 2;

        StringConsumer consumer = new StringConsumer(QUEUE, maxMessages);
        Future<List<String>> messages = consumer.consume();

        assertEquals(maxMessages, messages.get()
            .size());
    }

    @Test
    @Order(3)
    void givenMultipleMessages_whenPublishingWithConfirms_thenMessagesConfirmed() throws IOException, InterruptedException, TimeoutException {
        List<String> messages = generateRandomMessages(5);

        try (StringPublisher publisher = new StringPublisher(QUEUE)) {
            assertTrue(publisher.send(messages));
        }
    }

    @Test
    @Order(4)
    void givenSingleMessageLimit_whenConsumerWithAcks_thenLimitedMessagesAcked() throws IOException, TimeoutException, InterruptedException, ExecutionException {
        int maxMessages = 1;

        Future<List<String>> messages;
        StringConsumer consumer = new StringConsumer(QUEUE, maxMessages);
        messages = consumer.consume();

        assertEquals(maxMessages, messages.get()
            .size());
    }
}
