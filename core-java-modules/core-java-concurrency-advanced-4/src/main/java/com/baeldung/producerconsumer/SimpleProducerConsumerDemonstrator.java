package com.baeldung.producerconsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import static com.baeldung.producerconsumer.ThreadUtil.sleep;

public class SimpleProducerConsumerDemonstrator {
    BlockingQueue<Double> blockingQueue = new LinkedBlockingDeque<>(5);

    private void produce() {
        while (true) {
            double value = generateValue();
            try {
                blockingQueue.put(value);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
            System.out.printf("[%s] Value produced: %f\n", Thread.currentThread().getName(), value);
        }
    }

    private void consume() {
        while (true) {
            Double value;
            try {
                value = blockingQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
            // Consume value
            System.out.printf("[%s] Value consumed: %f\n", Thread.currentThread().getName(), value);
        }
    }

    private double generateValue() {
        return Math.random();
    }

    private void runProducerConsumer() {
        for (int i = 0; i < 2; i++) {
            Thread producerThread = new Thread(this::produce);
            producerThread.start();
        }

        for (int i = 0; i < 3; i++) {
            Thread consumerThread = new Thread(this::consume);
            consumerThread.start();
        }
    }

    public static void main(String[] args) {
        SimpleProducerConsumerDemonstrator simpleProducerConsumerDemonstrator = new SimpleProducerConsumerDemonstrator();
        simpleProducerConsumerDemonstrator.runProducerConsumer();
        sleep(2000);
        System.exit(0);
    }
}
