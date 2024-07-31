package com.baeldung.queue.dynamic;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class DynamicQueueCreation {

    private static final Logger log = LoggerFactory.getLogger(DynamicQueueCreation.class);

    private static final String QUEUE_NAME = "baeldung-queue";

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
            AMQP.Queue.DeclareOk declareOk = channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            log.info(declareOk.getQueue());

            AMQP.Queue.DeclareOk declareOkExists = channel.queueDeclarePassive(QUEUE_NAME);
            log.info(declareOkExists.getQueue());
        }
    }

}
