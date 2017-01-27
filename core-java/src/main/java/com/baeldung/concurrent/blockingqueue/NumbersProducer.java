package com.baeldung.concurrent.blockingqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class NumbersProducer implements Runnable {

    private final BlockingQueue<Integer> numbersQueue;

    public NumbersProducer(BlockingQueue<Integer> numbersQueue) {
        this.numbersQueue = numbersQueue;
    }

    public void run() {
        try {
            generateNumbers();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void generateNumbers() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            numbersQueue.put(ThreadLocalRandom.current().nextInt(100));
        }
    }
}