package com.baeldung.consumerackspubconfirms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Delivery;

public class SimpleSerialNumberConsumer {

    private static final Logger log = LoggerFactory.getLogger(SimpleSerialNumberConsumer.class);

    private final Connection connection;
    private final Channel channel;
    private final List<String> messages = new ArrayList<>();;
    private final String queue;
    private final int maximum;
    private boolean closed = false;

    public SimpleSerialNumberConsumer(String queue, int maximum) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();

        this.queue = queue;
        this.maximum = maximum;
        
        this.connection = factory.newConnection();
        this.channel = connection.createChannel();

        this.channel.basicQos(maximum);
        this.channel.queueDeclare(queue, false, false, false, null);
    }

    public Future<List<String>> consume() throws IOException {
        if (closed) {
            throw new IllegalStateException("already consumed");
        }

        CompletableFuture<List<String>> future = new CompletableFuture<>();

        log.info("waiting for {} messages...", maximum);
        channel.basicConsume(queue, false, (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");

            process(message, delivery);
            
            if (messages.size() >= maximum) {
                channel.basicAck(delivery.getEnvelope()
                    .getDeliveryTag(), true);

                log.info("* acknowledged consumption of {} messages", messages.size());
                future.complete(messages);
                close();
            }
        }, consumerTag -> {
            log.info("cancelled: {}", consumerTag);
        });

        return future;
    }

    private void close() throws IOException {
        if (closed) {
            return;
        }

        try {
            channel.close();
            connection.close();
            closed = true;
        } catch (TimeoutException e) {
            log.error("timed out", e);
        }
    }

    private void process(String message, Delivery delivery) {
        long deliveryTag = delivery.getEnvelope()
            .getDeliveryTag();

        messages.add(message);
        log.info("* [{}] processed: {}", deliveryTag, message);
    }
}
