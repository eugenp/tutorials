package com.baeldung.transmittablethreadlocal;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;
import com.alibaba.ttl.threadpool.TtlExecutors;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

import static org.junit.jupiter.api.Assertions.*;

public class TransmittableThreadLocalUnitTest {
    @Test
    void givenThreadLocal_whenTryingToGetValueFromAnotherThread_thenNullIsExpected() {

        ThreadLocal<String> transactionID = new ThreadLocal<>();
        transactionID.set(UUID.randomUUID().toString());

        new Thread(() -> assertNull(transactionID.get())).start();
    }

    @Test
    void givenInheritableThreadLocal_whenChangeTheTransactionIdAfterSubmissionToThreadPool_thenNewValueWillNotBeAvailableInParallelThread() {
        String firstTransactionIDValue = UUID.randomUUID().toString();
        InheritableThreadLocal<String> transactionID = new InheritableThreadLocal<>();
        transactionID.set(firstTransactionIDValue);
        Runnable task = () -> assertEquals(firstTransactionIDValue, transactionID.get());

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(task);

        String secondTransactionIDValue = UUID.randomUUID().toString();
        transactionID.set(secondTransactionIDValue);
        Runnable task2 = () -> assertNotEquals(secondTransactionIDValue, transactionID.get());
        executorService.submit(task2);

        executorService.shutdown();
    }

    @Test
    void givenTransmittableThreadLocal_whenTryingToGetValueFromAnotherThread_thenValueIsPresent() {

        TransmittableThreadLocal<String> transactionID = new TransmittableThreadLocal<>();
        transactionID.set(UUID.randomUUID().toString());

        new Thread(() -> assertNotNull(transactionID.get())).start();
    }

    @Test
    void givenTransmittableThreadLocal_whenChangeTheTransactionIdAfterSubmissionToThreadPool_thenNewValueWillBeAvailableInParallelThread() {

        String firstTransactionIDValue = UUID.randomUUID().toString();
        String secondTransactionIDValue = UUID.randomUUID().toString();

        TransmittableThreadLocal<String> transactionID = new TransmittableThreadLocal<>();
        transactionID.set(firstTransactionIDValue);

        Runnable task = () -> assertEquals(firstTransactionIDValue, transactionID.get());
        Runnable task2 = () -> assertEquals(secondTransactionIDValue, transactionID.get());

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(TtlRunnable.get(task));

        transactionID.set(secondTransactionIDValue);
        executorService.submit(TtlRunnable.get(task2));

        executorService.shutdown();
    }

    @Test
    void givenTransmittableThreadLocal_whenChangeTheTransactionIdAfterParallelStreamAlreadyProcessed_thenNewValueWillBeAvailableInTheSecondParallelStream() {

        String firstTransactionIDValue = UUID.randomUUID().toString();
        String secondTransactionIDValue = UUID.randomUUID().toString();

        TransmittableThreadLocal<String> transactionID = new TransmittableThreadLocal<>();
        transactionID.set(firstTransactionIDValue);

        TtlExecutors.getTtlExecutorService(new ForkJoinPool(4))
          .submit(
              () -> List.of(1, 2, 3, 4, 5)
                .parallelStream()
                .forEach(i -> assertEquals(firstTransactionIDValue, transactionID.get())));

        transactionID.set(secondTransactionIDValue);
        TtlExecutors.getTtlExecutorService(new ForkJoinPool(4))
          .submit(
              () -> List.of(1, 2, 3, 4, 5)
                .parallelStream()
                .forEach(i -> assertEquals(secondTransactionIDValue, transactionID.get())));
    }
}
