package com.baeldung.transferqueue;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

public class TransferQueueExampleMultipleProducersOneConsumer {
    static TransferQueue<String> transferQueue = new LinkedTransferQueue<>();

    public static void main(String[] args) {
        ExecutorService exService = Executors.newFixedThreadPool(3);
        Producer producer1 = new Producer(transferQueue, "1", 3);
        Producer producer2 = new Producer(transferQueue, "2", 3);
        Consumer consumer = new Consumer(transferQueue, "1", 6);

        exService.execute(producer1);
        exService.execute(producer2);
        exService.execute(consumer);

        exService.shutdown();
    }
}
