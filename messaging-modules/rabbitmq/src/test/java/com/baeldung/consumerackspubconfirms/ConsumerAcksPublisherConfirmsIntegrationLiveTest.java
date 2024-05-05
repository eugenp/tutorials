package com.baeldung.consumerackspubconfirms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ConsumerAcksPublisherConfirmsIntegrationLiveTest {

    static final String QUEUE = ConsumerAcksPublisherConfirmsIntegrationLiveTest.class.getName();

    @Test
    @Order(1)
    void whenPublishingWithConfirms_thenMessagesConfirmed() throws IOException, InterruptedException, TimeoutException {
        boolean confirmed = false;
        try (StringPublisher publisher = new StringPublisher(QUEUE)) {
            confirmed = publisher.send("hello") && publisher.send("world");
        }

        assertTrue(confirmed);
    }

    @Test
    @Order(2)
    void whenConsumerWithAcks_thenAllMessagesAcked() throws IOException, TimeoutException, InterruptedException, ExecutionException {
        StringConsumer consumer = new StringConsumer(QUEUE, 2);
        Future<List<String>> messages = consumer.consume();

        assertEquals(2, messages.get()
            .size());
    }

    @Test
    @Order(3)
    void givenMultipleMessages_whenPublishingWithConfirms_thenMessagesConfirmed() throws IOException, InterruptedException, TimeoutException {
        Random random = new Random();
        int length = 10;
        List<String> messages = random.ints(50, 0, 256)
            .mapToObj(i -> random.ints(length, 97, 123)
                .mapToObj(n -> String.valueOf((char) n))
                .collect(Collectors.joining()))
            .collect(Collectors.toList());

        boolean confirmed = false;
        try (StringPublisher publisher = new StringPublisher(QUEUE)) {
            confirmed = publisher.send(messages);
        }

        assertTrue(confirmed);
    }

    @Test
    @Order(4)
    void givenSingleMessageLimit_whenConsumerWithAcks_thenLimitedMessagesAcked() throws IOException, TimeoutException, InterruptedException, ExecutionException {
        Future<List<String>> messages;
        StringConsumer consumer = new StringConsumer(QUEUE, 1);
        messages = consumer.consume();

        assertEquals(1, messages.get()
            .size());
    }
}
