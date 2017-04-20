package com.baeldung.transferqueue;

import java.util.concurrent.TransferQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Consumer implements Runnable {
    private final TransferQueue<String> transferQueue;
    private final String name;
    private final int numberOfMessagesToConsume;
    public final AtomicInteger numberOfConsumedMessages = new AtomicInteger();

    public Consumer(TransferQueue<String> transferQueue, String name, int numberOfMessagesToConsume) {
        this.transferQueue = transferQueue;
        this.name = name;
        this.numberOfMessagesToConsume = numberOfMessagesToConsume;
    }

    @Override
    public void run() {
        for (int i = 0; i < numberOfMessagesToConsume; i++) {
            try {
                System.out.println("Consumer: " + name + " is waiting to take element...");
                String element = transferQueue.take();
                longProcessing(element);
                System.out.println("Consumer: " + name + " received element: " + element);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void longProcessing(String element) throws InterruptedException {
        numberOfConsumedMessages.incrementAndGet();
        Thread.sleep(500);
    }
}