package com.baeldung.consumerackspubconfirms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Delivery;

public class StringConsumer {

    private final Connection connection;
    private final Channel channel;
    private final List<String> messages = new ArrayList<>();;
    private final String queue;
    private final int maximum;
    private boolean closed = false;

    public StringConsumer(String queue, int maximum) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();

        this.queue = queue;
        this.maximum = maximum;
        
        this.connection = factory.newConnection();
        this.channel = connection.createChannel();

        this.channel.basicQos(maximum);
        this.channel.queueDeclare(queue, false, false, false, null);
    }

    public Future<List<String>> consume() throws IOException {
        if (closed)
            throw new IllegalStateException("already consumed");

        final AtomicInteger consumed = new AtomicInteger(0);
        final CompletableFuture<List<String>> future = new CompletableFuture<>();

        System.out.printf("waiting for %d messages...\n", maximum);
        channel.basicConsume(queue, false, (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");

            process(message, delivery);
            
            if (consumed.incrementAndGet() >= maximum) {
                channel.basicAck(delivery.getEnvelope()
                    .getDeliveryTag(), true);

                System.out.printf("* acknowledged consumption of %d messages\n", consumed.get());
                future.complete(messages);
                close();
            }
        }, consumerTag -> {
            System.out.printf("cancelled: %s\n", consumerTag);
        });

        return future;
    }

    private void close() throws IOException {
        try {
            if (closed)
                return;

            channel.close();
            connection.close();
            closed = true;
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    private void process(String message, Delivery delivery) {
        long deliveryTag = delivery.getEnvelope()
            .getDeliveryTag();

        messages.add(message);
        System.out.printf("* [%d] processed: %s\n", deliveryTag, message);
    }
}
