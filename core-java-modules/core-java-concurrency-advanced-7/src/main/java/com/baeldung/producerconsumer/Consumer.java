package com.baeldung.producerconsumer;

import java.util.logging.Logger;

public class Consumer implements Runnable {

    private static final Logger log =
            Logger.getLogger(Consumer.class.getCanonicalName());

    private volatile boolean running = false;

    private final DataQueue dataQueue;

    public Consumer(DataQueue dataQueue) {
        this.dataQueue = dataQueue;
    }

    @Override
    public void run() {
        running = true;
        consume();
    }

    public void stop() {
        running = false;
    }

    public void consume() {

        while (running) {

            try {

                Message message = dataQueue.poll();

                useMessage(message);

                ThreadUtil.sleep((long) (Math.random() * 100));

            } catch (InterruptedException e) {

                log.severe("Error while consuming messages.");

                Thread.currentThread().interrupt();

                break;
            }
        }

        log.info("Consumer Stopped");
    }

    private void useMessage(Message message) {

        if (message != null) {

            log.info(String.format(
                    "[%s] Consuming Message. Id: %d, Data: %f%n",
                    Thread.currentThread().getName(),
                    message.getId(),
                    message.getData()));
        }
    }
}