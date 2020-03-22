package com.baeldung.guava.concurrency;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListenableFutureExampleUnitTest {

    private static Logger logger = Logger.getAnonymousLogger();

    @Test
    public void whenNoneZeroDenominator_thenPositiveQuotientReturned() {
        int result = result(30, 10);
        assertEquals(3, result);
    }

    @Test
    public void whenZeroDenominator_thenMinusOneReturned() {
        int result = result(30, 0);
        assertEquals(-1, result);
    }

    private static int result(int numerator, int denominator) {
        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(5));
        ListenableFuture<Integer> future = service.submit(() -> ListenableFutureExample.quotient(numerator, denominator));

        return ListenableFutureExample.compute(service, future);
    }

    @Test
    public void whenEggsCollected_thenTotalAddsUp() throws ExecutionException, InterruptedException {
        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(5));
        List<ListenableFuture<Integer>> futures = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            int id = i;
            futures.add(service.submit(() -> new ListenableFutureExample.EggCollector(id).collectEgg()));
        }
        service.shutdown();
        ListenableFuture<List<Integer>> resultFuture = Futures.allAsList(futures);
        int total = resultFuture.get().stream().reduce(0, (subtotal, element) -> subtotal + element);
        logger.info("Total eggs collected: " + total);
    }

}