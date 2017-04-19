package com.baeldung.transferqueue;

import org.junit.Test;

import java.util.concurrent.*;

public class TransferQueueTest {

    @Test
    public void givenTransferQueue_whenUseMultipleConsumersAndMultipleProducers_thenShouldProcessAllMessages() throws InterruptedException {
        //given
        TransferQueue<String> transferQueue = new LinkedTransferQueue<>();
        ExecutorService exService = Executors.newFixedThreadPool(3);
        Producer producer1 = new Producer(transferQueue, "1", 3);
        Producer producer2 = new Producer(transferQueue, "2", 3);
        Consumer consumer1 = new Consumer(transferQueue, "1", 3);
        Consumer consumer2 = new Consumer(transferQueue, "2", 3);

        //when
        exService.execute(producer1);
        exService.execute(producer2);
        exService.execute(consumer1);
        exService.execute(consumer2);

        //then
        exService.awaitTermination(10_000, TimeUnit.MILLISECONDS);
        exService.shutdown();
    }

    @Test
    public void givenTransferQueue_whenUseOneConsumerAndOneProducer_thenShouldProcessAllMessages() throws InterruptedException {
        //given
        TransferQueue<String> transferQueue = new LinkedTransferQueue<>();
        ExecutorService exService = Executors.newFixedThreadPool(2);
        Producer producer = new Producer(transferQueue, "1", 3);
        Consumer consumer = new Consumer(transferQueue, "1", 3);

        //when
        exService.execute(producer);
        exService.execute(consumer);

        //then
        exService.awaitTermination(5000, TimeUnit.MILLISECONDS);
        exService.shutdown();
    }

    @Test
    public void givenTransferQueue_whenUseOneProducerAndNoConsumers_thenShouldFailWithTimeout() throws InterruptedException {
        //given
        TransferQueue<String> transferQueue = new LinkedTransferQueue<>();
        ExecutorService exService = Executors.newFixedThreadPool(2);
        Producer producer = new Producer(transferQueue, "1", 3);

        //when
        exService.execute(producer);

        //then
        exService.awaitTermination(5000, TimeUnit.MILLISECONDS);
        exService.shutdown();
    }

}