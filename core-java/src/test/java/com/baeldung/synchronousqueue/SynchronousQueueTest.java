package com.baeldung.synchronousqueue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static junit.framework.TestCase.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SynchronousQueueTest {

    @Test
    public void givenTwoThreads_whenWantToExchangeUsingLockGuardedVariable_thenItSucceed() throws InterruptedException {
        //given
        ExecutorService executor = Executors.newFixedThreadPool(2);
        AtomicInteger sharedState = new AtomicInteger();
        CountDownLatch countDownLatch = new CountDownLatch(1);

        Runnable producer = () -> {
            Integer producedElement = ThreadLocalRandom.current().nextInt();
            System.out.println("Saving an element: " + producedElement + " to the exchange point");
            sharedState.set(producedElement);
            countDownLatch.countDown();
        };

        Runnable consumer = () -> {
            try {
                countDownLatch.await();
                Integer consumedElement = sharedState.get();
                System.out.println("consumed an element: " + consumedElement + " from the exchange point");
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        };

        //when
        executor.execute(producer);
        executor.execute(consumer);

        //then
        executor.awaitTermination(500, TimeUnit.MILLISECONDS);
        executor.shutdown();
        assertEquals(countDownLatch.getCount(), 0);
    }

    @Test
    public void givenTwoThreads_whenWantToExchangeUsingSynchronousQueue_thenItSucceed() throws InterruptedException {
        //given
        ExecutorService executor = Executors.newFixedThreadPool(2);
        final SynchronousQueue<Integer> queue = new SynchronousQueue<>();

        Runnable producer = () -> {
            Integer producedElement = ThreadLocalRandom.current().nextInt();
            try {
                System.out.println("Saving an element: " + producedElement + " to the exchange point");
                queue.put(producedElement);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        };

        Runnable consumer = () -> {
            try {
                Integer consumedElement = queue.take();
                System.out.println("consumed an element: " + consumedElement + " from the exchange point");
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        };

        //when
        executor.execute(producer);
        executor.execute(consumer);

        //then
        executor.awaitTermination(500, TimeUnit.MILLISECONDS);
        executor.shutdown();
        assertEquals(queue.size(), 0);
    }
}