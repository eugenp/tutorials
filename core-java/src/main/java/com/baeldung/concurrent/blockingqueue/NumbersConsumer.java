package com.baeldung.concurrent.blockingqueue;


import java.util.concurrent.BlockingQueue;

class NumbersConsumer implements Runnable {
    private final BlockingQueue<Integer> queue;

    public NumbersConsumer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            while (true) {
                Integer number = queue.take();
                String result = number.toString();
                System.out.println(Thread.currentThread().getName() + " result: " + result);

            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}