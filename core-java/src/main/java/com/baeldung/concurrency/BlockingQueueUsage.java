package com.baeldung.concurrency;


import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingQueueUsage {
    public static void main(String[] args) {
        int BOUND = 10;
        int N_PRODUCERS = 4;
        int N_CONSUMERS = Runtime.getRuntime().availableProcessors();


        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(BOUND);

        for (int i = 0; i < N_PRODUCERS; i++) {
            System.out.println("new Producer");
            new Thread(new NumbersProducer(queue)).start();
        }

        for (int j = 0; j < N_CONSUMERS; j++) {
            System.out.println("new Consumer");
            new Thread(new NumbersConsumer(queue)).start();
        }
    }


    //producer
    static class NumbersProducer implements Runnable {
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

    //consumer
    static class NumbersConsumer implements Runnable {
        private final BlockingQueue<Integer> queue;

        public NumbersConsumer(BlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        public void run() {
            try {
                while (true) {
                    Integer number = queue.take();
                    String result = number.toString();
                    System.out.println(Thread.currentThread().getName()  + " result: " + result);

                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }
}