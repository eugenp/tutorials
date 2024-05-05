package com.baeldung.consumerackspubconfirms;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class StringPublisher implements AutoCloseable {

    private final String queue;
    private final Connection connection;
    private final Channel channel;

    public StringPublisher(String queue) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        this.queue = queue;
        this.connection = factory.newConnection();

        this.channel = connection.createChannel();
        this.channel.queueDeclare(queue, false, false, false, null);

        this.channel.confirmSelect();
    }

    public boolean send(String message) throws IOException, InterruptedException, TimeoutException {
        try {
            channel.basicPublish("", queue, null, message.getBytes());
            return channel.waitForConfirms(1000);
        } finally {
            System.out.printf("* sent %s\n", message);
        }
    }

    public boolean send(List<String> messages) throws IOException, InterruptedException, TimeoutException {
        for (String message : messages) {
            channel.basicPublish("", queue, null, message.getBytes());
        }

        channel.waitForConfirmsOrDie();
        System.out.printf("* sent %d messages\n", messages.size());
        return true;
    }

    @Override
    public void close() throws IOException, TimeoutException {
        channel.close();
        connection.close();
    }
}