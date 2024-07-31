package com.baeldung.concurrent.delayqueue;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;


public class DelayQueueProducer implements Runnable {
    private BlockingQueue<DelayObject> queue;
    private final Integer numberOfElementsToProduce;
    private final Integer delayOfEachProducedMessageMilliseconds;

    DelayQueueProducer(BlockingQueue<DelayObject> queue,
                       Integer numberOfElementsToProduce,
                       Integer delayOfEachProducedMessageMilliseconds) {
        this.queue = queue;
        this.numberOfElementsToProduce = numberOfElementsToProduce;
        this.delayOfEachProducedMessageMilliseconds = delayOfEachProducedMessageMilliseconds;
    }

    @Override
    public void run() {
        for (int i = 0; i < numberOfElementsToProduce; i++) {
            DelayObject object
                    = new DelayObject(UUID.randomUUID().toString(), delayOfEachProducedMessageMilliseconds);
            System.out.println("Put object = " + object);
            try {
                queue.put(object);
                Thread.sleep(500);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }
}