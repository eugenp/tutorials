package com.baeldung.concurrent.blockingqueue;


import java.util.Random;
import java.util.concurrent.BlockingQueue;


class NumbersProducer implements Runnable {
    private final Random random = new Random();
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
            numbersQueue.put(random.nextInt(100));
        }
    }
}