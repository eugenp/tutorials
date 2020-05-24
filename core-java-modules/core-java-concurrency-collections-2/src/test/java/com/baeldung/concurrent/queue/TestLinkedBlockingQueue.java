package com.baeldung.concurrent.queue;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.junit.FixMethodOrder;
import org.junit.Test;

@FixMethodOrder
public class TestLinkedBlockingQueue {

    @Test
    public void givenThereIsExistingCollection_WhenAddedIntoQueue_ThenShouldContainElements() {
        Collection<Integer> elements = Arrays.asList(1, 2, 3, 4, 5);
        LinkedBlockingQueue<Integer> linkedBlockingQueue = new LinkedBlockingQueue<>(elements);
        assertThat(linkedBlockingQueue).containsExactly(1, 2, 3, 4, 5);
    }

    @Test
    public void givenQueueIsEmpty_WhenAccessingTheQueue_ThenThreadBlocks() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        LinkedBlockingQueue<Integer> linkedBlockingQueue = new LinkedBlockingQueue<>();
        executorService.submit(() -> {
            try {
                linkedBlockingQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        TimeUnit.SECONDS.sleep(1);
        executorService.awaitTermination(1, TimeUnit.SECONDS);
        executorService.shutdown();
    }

    @Test
    public void givenProducerPutsElementInQueue_WhenConsumerAccessQueue_ThenItRetrieve() {
        int element = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        LinkedBlockingQueue<Integer> linkedBlockingQueue = new LinkedBlockingQueue<>();
        Runnable putTask = () -> {
            try {
                linkedBlockingQueue.put(element);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Callable<Integer> takeTask = () -> {
            try {
                return linkedBlockingQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        };

        executorService.submit(putTask);
        Future<Integer> returnElement = executorService.submit(takeTask);
        try {
            TimeUnit.SECONDS.sleep(1);
            assertThat(returnElement.get()
                .intValue(), is(equalTo(element)));
            executorService.awaitTermination(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        executorService.shutdown();
    }
}
