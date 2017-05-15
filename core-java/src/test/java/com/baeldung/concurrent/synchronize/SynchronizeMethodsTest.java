package com.baeldung.concurrent.synchronize;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import org.junit.Test;

public class SynchronizeMethodsTest {

    @Test
    public void givenMultiThread_whenNonSyncMethod() {
        ExecutorService service = Executors.newFixedThreadPool(3);
        SynchronizedMethods method = new SynchronizedMethods();

        IntStream.range(0, 10)
            .forEach(count -> service.submit(method::calculate));
        service.shutdown();

        assertEquals(10, method.getSum());
    }

    @Test
    public void givenMultiThread_whenMethodSync() {
        ExecutorService service = Executors.newFixedThreadPool(3);
        SynchronizedMethods method = new SynchronizedMethods();

        IntStream.range(0, 10)
            .forEach(count -> service.submit(method::synchronisedCalculate));
        service.shutdown();

        assertEquals(10, method.getSum());
    }

    @Test
    public void givenMultiThread_whenStaticSycnMehtod() {
        ExecutorService service = Executors.newCachedThreadPool();

        IntStream.range(0, 10)
            .forEach(count -> service.submit(SynchronizedMethods::syncStaticCalculate));
        service.shutdown();

        assertEquals(10, SynchronizedMethods.staticSum);
    }

}
