package com.baeldung.producerconsumer;

import java.util.ArrayList;
import java.util.List;

import static com.baeldung.producerconsumer.ThreadUtil.*;

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

        // let threads run for two seconds
        sleep(2000);

        // stop threads
        producer.stop();
        consumer.stop();

        waitForAllThreadsToComplete(threads);
    }

    public static void demoMultipleProducersAndMultipleConsumers() {
        DataQueue dataQueue = new DataQueue(MAX_QUEUE_CAPACITY);
        int producerCount = 5;
        int consumerCount = 5;
        List<Thread> threads = new ArrayList<>();
        List<Producer> producers = new ArrayList<>();
        List<Consumer> consumers = new ArrayList<>();

        for(int i = 0; i < producerCount; i++) {
            Producer producer = new Producer(dataQueue);
            Thread producerThread = new Thread(producer);
            producerThread.start();
            threads.add(producerThread);
            producers.add(producer);
        }

        for(int i = 0; i < consumerCount; i++) {
            Consumer consumer = new Consumer(dataQueue);
            Thread consumerThread = new Thread(consumer);
            consumerThread.start();
            threads.add(consumerThread);
            consumers.add(consumer);
        }

        // let threads run for ten seconds
        sleep(10000);

        // stop threads
        consumers.forEach(Consumer::stop);
        producers.forEach(Producer::stop);

        waitForAllThreadsToComplete(threads);
    }

    public static void main(String[] args) {
        demoSingleProducerAndSingleConsumer();
        demoMultipleProducersAndMultipleConsumers();
    }
}
