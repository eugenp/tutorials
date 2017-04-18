package com.baeldung.transferqueue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

public class TransferQueueExampleOneProducerOneConsumer {
    static TransferQueue<String> transferQueue = new LinkedTransferQueue<>();

    public static void main(String[] args) {
        ExecutorService exService = Executors.newFixedThreadPool(2);
        Producer producer = new Producer(transferQueue, "1", 3);
        Consumer consumer = new Consumer(transferQueue, "1", 3);

        exService.execute(producer);
        exService.execute(consumer);

        exService.shutdown();
    }

}