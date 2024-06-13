package com.baeldung.consumerackspubconfirms;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.consumerackspubconfirms.helper.PendingMessage;
import com.rabbitmq.client.Channel;

public class UuidPublisher {

    private static final Logger log = LoggerFactory.getLogger(UuidPublisher.class);
    private static final int MAX_TRIES = 2;
    private static final long TIMEOUT = TimeUnit.SECONDS.toMillis(1);

    private final Channel channel;
    private final String queue;
    private ConcurrentNavigableMap<Long, PendingMessage> pendingDelivery = new ConcurrentSkipListMap<>();

    public UuidPublisher(Channel channel, String queue) throws IOException {
        this.channel = channel;
        this.queue = queue;

        this.channel.confirmSelect();
    }

    public boolean send(String message) throws IOException, InterruptedException, TimeoutException {
        try {
            channel.basicPublish("", queue, null, message.getBytes());
            return channel.waitForConfirms(TIMEOUT);
        } finally {
            log.info("* sent {}", message);
        }
    }

    public void sendAllOrDie(List<String> messages) throws IOException, InterruptedException, TimeoutException {
        for (String message : messages) {
            channel.basicPublish("", queue, null, message.getBytes());
        }

        channel.waitForConfirmsOrDie(TIMEOUT);
        log.info("* sent {} messages", messages.size());
    }

    public void sendOrRetry(List<String> messages) throws IOException {
        sendOrRetry(messages, null);
    }

    public void sendOrRetry(List<String> messages, CountDownLatch latch) throws IOException {
        createConfirmListener(latch);

        for (String message : messages) {
            long tag = channel.getNextPublishSeqNo();
            pendingDelivery.put(tag, new PendingMessage(message));

            channel.basicPublish("", queue, null, message.getBytes());
            log.trace("sent: " + message);
        }
        log.debug("batch sent, {} confirms pending", pendingDelivery.size());
    }

    private void createConfirmListener(CountDownLatch latch) {
        this.channel.addConfirmListener((tag, multiple) -> {
            ConcurrentNavigableMap<Long, PendingMessage> confirmed = pendingDelivery.headMap(tag, true);
            log.debug("[{}#{}/{}] messages acked: {}", multiple, tag, pendingDelivery.size(), confirmed.size());
            confirmed.clear();

            if (latch != null && pendingDelivery.isEmpty()) {
                log.info("publishing complete");
                latch.countDown();
            }
        }, (tag, multiple) -> {
            ConcurrentNavigableMap<Long, PendingMessage> failed = pendingDelivery.headMap(tag, true);
            log.debug("[{}#{}/{}] messages nacked: {}", multiple, tag, pendingDelivery.size(), failed.size());

            failed.values()
                .removeIf(pending -> {
                    pending.incrementTries();
                    boolean giveUp = pending.getTries() >= MAX_TRIES;
                    log.warn("retry info: {}", pending);
                    return giveUp;
                });

            if (pendingDelivery.isEmpty()) {
                log.info("publishing completed with nacks");
                if (latch != null) {
                    latch.countDown();
                }
            } else {
                publishPending();
            }
        });
    }

    private void publishPending() {
        log.info("retrying failed deliveries...");
        pendingDelivery.entrySet()
            .removeIf(entry -> {
                long tag = entry.getKey();
                PendingMessage message = entry.getValue();

                try {
                    log.debug("{} - retried, {} remaining", message, pendingDelivery.size());
                    channel.basicPublish("", queue, null, message.getBody()
                        .getBytes());
                    return false;
                } catch (IOException e) {
                    log.error("can't publish #" + tag, e);
                    return true;
                }
            });
    }
}