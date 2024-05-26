package com.baeldung.consumerackspubconfirms;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Delivery;

public class SerialNumberConsumer {

    private static final Logger log = LoggerFactory.getLogger(SerialNumberConsumer.class);

    private final String queue;
    private final int batchSize;
    private final Channel channel;

    public SerialNumberConsumer(Channel channel, String queue, int batchSize) throws IOException {
        this.queue = queue;
        this.batchSize = batchSize;
        
        channel.basicQos(batchSize);
        channel.queueDeclare(queue, false, false, false, null);
        this.channel = channel;
    }

    public void consume() throws IOException {
        consume(null);
    }

    public void consume(CountDownLatch latch) throws IOException {
        log.info("waiting for {} messages...", batchSize);

        // channel.basicGet(queue, false);

        channel.basicConsume(queue, false, (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");

            long deliveryTag = delivery.getEnvelope()
                .getDeliveryTag();
            if (!process(message, delivery)) {
                channel.basicNack(deliveryTag, false, false);
            }
            
            if (deliveryTag % batchSize == 0) {
                channel.basicAck(deliveryTag, true);

                log.info("* acknowledged consumption of {} messages", deliveryTag);
                if (latch != null) {
                    latch.countDown();
                }
            }
        }, consumerTag -> {
            log.info("cancelled: {}", consumerTag);
        });
    }

    private boolean process(String message, Delivery delivery) {
        long deliveryTag = delivery.getEnvelope()
            .getDeliveryTag();

        try {
            log.warn("* [{}] invalid: {}", deliveryTag, message);
            UUID.fromString(message);
        } catch (IllegalArgumentException e) {
            return false;
        }

        log.info("* [{}] processed: {}", deliveryTag, message);
        return true;
    }
}
