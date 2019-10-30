package com.baeldung.concurrent.synchronize;

import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class BaeldungSynchronizeMethodsUnitTest {

    @Test
    @Ignore
    public void givenMultiThread_whenNonSyncMethod() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(3);
        BaeldungSynchronizedMethods method = new BaeldungSynchronizedMethods();

        IntStream.range(0, 1000)
          .forEach(count -> service.submit(method::calculate));
        service.awaitTermination(100, TimeUnit.MILLISECONDS);

        assertEquals(1000, method.getSum());
    }

    @Test
    public void givenMultiThread_whenMethodSync() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(3);
        BaeldungSynchronizedMethods method = new BaeldungSynchronizedMethods();

        IntStream.range(0, 1000)
          .forEach(count -> service.submit(method::synchronisedCalculate));
        service.awaitTermination(100, TimeUnit.MILLISECONDS);

        assertEquals(1000, method.getSyncSum());
    }

    @Test
    public void givenMultiThread_whenStaticSyncMethod() throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();

        IntStream.range(0, 1000)
          .forEach(count -> service.submit(BaeldungSynchronizedMethods::syncStaticCalculate));
        service.awaitTermination(100, TimeUnit.MILLISECONDS);

        assertEquals(1000, BaeldungSynchronizedMethods.staticSum);
    }

}
