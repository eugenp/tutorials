package com.baeldung.consumerackspubconfirms;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;

public class UuidConsumer implements AutoCloseable {

    private static final Logger log = LoggerFactory.getLogger(UuidConsumer.class);
    private static final int WATCHER_INTERVAL_SECONDS = 5;

    private final Channel channel;
    private final String queue;
    private final int batchSize;
    private final AtomicLong pendingTag = new AtomicLong();
    private ScheduledExecutorService scheduler;

    public UuidConsumer(Channel channel, String queue, int batchSize) throws IOException {
        this.channel = channel;
        this.queue = queue;
        this.batchSize = batchSize;

        channel.basicQos(batchSize);
    }

    public void consume() throws IOException {
        consume(null);
    }

    public void consume(CountDownLatch latch) throws IOException {
        createWatcherThread(latch);

        log.debug("creating consumer with batch ack size {}", batchSize);
        channel.basicConsume(queue, false, (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            long deliveryTag = delivery.getEnvelope()
                .getDeliveryTag();

            synchronized (pendingTag) {
                if (!process(message, deliveryTag)) {
                    if (pendingTag.get() != 0) {
                        channel.basicAck(pendingTag.get(), true);
                        log.info("* acked up to #{} before nacking #{}", pendingTag.get(), deliveryTag);
                        pendingTag.set(0);
                    }

                    channel.basicReject(deliveryTag, false);
                    log.info("* nacked #{}", deliveryTag);
                } else if (deliveryTag % batchSize == 0) {
                    channel.basicAck(deliveryTag, true);
                    pendingTag.set(0);

                    log.info("* acked up to #{}", deliveryTag);
                } else {
                    pendingTag.set(deliveryTag);
                    log.debug("* pending acks up to #{}", pendingTag);
                }

                if (latch != null) {
                    latch.countDown();
                }
            }
            log.trace("listener lock released. pendingTag: #{}", pendingTag.get());
        }, consumerTag -> {
            log.warn("cancelled: #{}", consumerTag);
        });
    }

    @Override
    public void close() throws Exception {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
    }

    private void createWatcherThread(CountDownLatch latch) {
        log.debug("creating watcher thread");

        this.scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            try {
                log.trace("watcher: waiting for lock on possible pendingTag");
                synchronized (pendingTag) {
                    log.debug("watcher: checking pendingTag #{}/{}", pendingTag, scheduler.toString());
                    if (pendingTag.get() == 0) {
                        return;
                    }

                    try {
                        channel.basicAck(pendingTag.get(), true);
                        log.info("watcher: acked up to #{}", pendingTag.get());
                        pendingTag.set(0);
                    } catch (IOException e) {
                        log.error("watcher error: acking up to #{}", pendingTag);
                    }
                }
            } finally {
                if (latch != null) {
                    latch.countDown();
                }
            }
        }, WATCHER_INTERVAL_SECONDS, WATCHER_INTERVAL_SECONDS, TimeUnit.SECONDS);
    }

    private boolean process(String message, long deliveryTag) {
        try {
            UUID.fromString(message);
            log.debug("* [#{}] processed: {}", deliveryTag, message);
        } catch (IllegalArgumentException e) {
            log.warn("* [#{}] invalid: {}", deliveryTag, message);
            return false;
        }

        return true;
    }
}
