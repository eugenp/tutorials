package com.baeldung.benchmark.queue.dynamic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class DynamicQueueCreationLiveTest {

    private static final String QUEUE_NAME = "baeldung-queue";
    private static final String QUEUE_NAME_NEW = "baeldung-queue-new";

    private static Connection connection;

    @BeforeAll
    public static void setUpConnection() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
    }

    @Test
    void givenQueueName_whenCreatingQueue_thenCheckingIfQueueCreated() throws IOException, TimeoutException {

        try (Channel channel = connection.createChannel()) {
            AMQP.Queue.DeclareOk declareOk = channel.queueDeclare(QUEUE_NAME, true, false, false, null);

            assertNotNull(declareOk);
            assertEquals(QUEUE_NAME, declareOk.getQueue());
        }
    }

    @Test
    void givenQueueName_whenCreatingQueue_thenCheckingIfQueueExists() throws IOException, TimeoutException {

        try (Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);

            AMQP.Queue.DeclareOk declareOk = channel.queueDeclarePassive(QUEUE_NAME);

            assertNotNull(declareOk);
            assertEquals(QUEUE_NAME, declareOk.getQueue());
        }
    }

    @Test
    void givenQueueName_whenQueueDoesNotExist_thenCheckingIfQueueExists() throws IOException, TimeoutException {

        try (Channel channel = connection.createChannel()) {
            assertThrows(IOException.class, () -> {
                channel.queueDeclarePassive(QUEUE_NAME_NEW);
            });
        }
    }

    @AfterAll
    public static void destroyConnection() throws IOException {
        connection.close();
    }
}
