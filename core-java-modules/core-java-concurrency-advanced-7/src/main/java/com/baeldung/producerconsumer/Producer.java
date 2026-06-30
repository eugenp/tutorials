package com.baeldung.producerconsumer;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class Producer implements Runnable {

    private static final Logger log =
            Logger.getLogger(Producer.class.getCanonicalName());

    private static final AtomicInteger idSequence =
            new AtomicInteger(0);

    private volatile boolean running = false;

    private final DataQueue dataQueue;

    public Producer(DataQueue dataQueue) {
        this.dataQueue = dataQueue;
    }

    @Override
    public void run() {
        running = true;
        produce();
    }

    public void stop() {
        running = false;
    }

    public void produce() {

        while (running) {

            try {

                dataQueue.add(generateMessage());

                log.info("Size of the queue is: "
                        + dataQueue.getSize());

                ThreadUtil.sleep((long) (Math.random() * 100));

            } catch (InterruptedException e) {

                log.severe("Error while producing messages.");

                Thread.currentThread().interrupt();

                break;
            }
        }

        log.info("Producer Stopped");
    }

    private Message generateMessage() {

        Message message =
                new Message(idSequence.incrementAndGet(),
                        Math.random());

        log.info(String.format(
                "[%s] Generated Message. Id: %d, Data: %f%n",
                Thread.currentThread().getName(),
                message.getId(),
                message.getData()));

        return message;
    }
}