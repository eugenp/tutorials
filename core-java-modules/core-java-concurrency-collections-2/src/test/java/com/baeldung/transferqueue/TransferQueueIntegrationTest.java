package com.baeldung.transferqueue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;

import static junit.framework.TestCase.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TransferQueueIntegrationTest {

    @Test
    public void whenMultipleConsumersAndProducers_thenProcessAllMessages() throws InterruptedException {
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
        exService.awaitTermination(5000, TimeUnit.MILLISECONDS);
        exService.shutdown();

        assertEquals(producer1.numberOfProducedMessages.intValue(), 3);
        assertEquals(producer2.numberOfProducedMessages.intValue(), 3);
    }

    @Test
    public void whenUseOneConsumerAndOneProducer_thenShouldProcessAllMessages() throws InterruptedException {
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

        assertEquals(producer.numberOfProducedMessages.intValue(), 3);
        assertEquals(consumer.numberOfConsumedMessages.intValue(), 3);
    }

    @Test
    public void whenUseOneProducerAndNoConsumers_thenShouldFailWithTimeout() throws InterruptedException {
        //given
        TransferQueue<String> transferQueue = new LinkedTransferQueue<>();
        ExecutorService exService = Executors.newFixedThreadPool(2);
        Producer producer = new Producer(transferQueue, "1", 3);

        //when
        exService.execute(producer);

        //then
        exService.awaitTermination(5000, TimeUnit.MILLISECONDS);
        exService.shutdown();

        assertEquals(producer.numberOfProducedMessages.intValue(), 0);
    }
}
