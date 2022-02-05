package com.baeldung.producerconsumer;

import java.util.ArrayList;
import java.util.List;

public class ProducerConsumerDemonstrator {
    private static final int MAX_QUEUE_CAPACITY = 5;

    public static void demoSingleProducerAndSingleConsumer() {
        DataQueue dataQueue = new DataQueue(MAX_QUEUE_CAPACITY);

        Producer producer = new Producer(dataQueue);
        Thread producerThread = new Thread(producer);

        Consumer consumer = new Consumer(dataQueue);
        Thread consumerThread = new Thread(consumer);

        producerThread.start();
        consumerThread.start();

        List<Thread> threads = new ArrayList<>();
        threads.add(producerThread);
        threads.add(consumerThread);

        try {
            // Wait for some time to demonstrate threads
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Stop threads
        producer.stop();
        consumer.stop();
        dataQueue.stop();

        waitForAllThreadsToComplete(threads);
    }

    private static void waitForAllThreadsToComplete(List<Thread> threads) {
        for(Thread thread: threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void demoMultipleProducersAndMultipleConsumers() {
        DataQueue dataQueue = new DataQueue(MAX_QUEUE_CAPACITY);
        int producerCount = 3;
        int consumerCount = 3;
        List<Thread> threads = new ArrayList<>();
        Producer[] producers = new Producer[producerCount];
        for(int i = 0; i < producerCount; i++) {
            Producer producer = new Producer(dataQueue);
            producers[i] = producer;
            Thread producerThread = new Thread(producer);
            producerThread.start();
            threads.add(producerThread);
        }
        Consumer[] consumers = new Consumer[consumerCount];
        for(int i = 0; i < consumerCount; i++) {
            Consumer consumer = new Consumer(dataQueue);
            consumers[i] = consumer;
            Thread consumerThread = new Thread(consumer);
            consumerThread.start();
            threads.add(consumerThread);
        }

        try {
            // Wait for some time to demonstrate threads
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Stop threads
        for(int i = 0; i < producerCount; i++) {
            producers[i].stop();
        }
        for(int i = 0; i < consumerCount; i++) {
            consumers[i].stop();
        }
        dataQueue.stop();
        waitForAllThreadsToComplete(threads);
    }

    public static void main(String[] args) {
        demoSingleProducerAndSingleConsumer();
        demoMultipleProducersAndMultipleConsumers();
    }
}
