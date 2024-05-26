package com.baeldung.consumerackspubconfirms;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class SerialNumberPublisher implements AutoCloseable {

    private static final Logger log = LoggerFactory.getLogger(SerialNumberPublisher.class);

    private final Connection connection;
    private final Channel channel;
    private final String queue;

    public SerialNumberPublisher(String queue) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        this.queue = queue;
        this.connection = factory.newConnection();

        this.channel = connection.createChannel();
        this.channel.queueDeclare(queue, false, false, false, null);

        this.channel.confirmSelect();
        this.channel.addConfirmListener((deliveryTag, multiple) -> {
            // code when message is confirmed
            ConcurrentNavigableMap<Long, String> outstandingConfirms = new ConcurrentSkipListMap<>();
            // outstandingConfirms.headMap(null);
            // https://www.rabbitmq.com/tutorials/tutorial-seven-java
        }, (sequenceNumber, multiple) -> {
            // code when message is nack-ed
        });
    }

    public boolean send(String message) throws IOException, InterruptedException, TimeoutException {
        try {
            channel.basicPublish("", queue, null, message.getBytes());
            return channel.waitForConfirms(1000);
        } finally {
            log.info("* sent {}", message);
        }
    }

    public boolean send(List<String> messages) throws IOException, InterruptedException, TimeoutException {
        for (String message : messages) {
            channel.basicPublish("", queue, null, message.getBytes());
        }

        channel.waitForConfirmsOrDie();
        log.info("* sent {} messages", messages.size());
        return true;
    }

    @Override
    public void close() throws IOException, TimeoutException {
        channel.close();
        connection.close();
    }
}