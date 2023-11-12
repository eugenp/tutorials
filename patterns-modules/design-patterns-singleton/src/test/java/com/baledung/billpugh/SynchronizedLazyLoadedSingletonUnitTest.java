package com.baledung.billpugh;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SynchronizedLazyLoadedSingletonUnitTest {
    Logger logger = LoggerFactory.getLogger(SynchronizedLazyLoadedSingletonUnitTest.class);
    @Test
    void giveSynchronizedLazyLoadedImpl_whenCallgetInstance_thenReturnSingleton() {
        Set<SynchronizedLazyLoadedSingleton> setHoldingSingletonObj = new HashSet<>();
        List<Future<SynchronizedLazyLoadedSingleton>> futures = new ArrayList<>();

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Callable<SynchronizedLazyLoadedSingleton> runnableTask = () -> {
            logger.info("run called for:" + Thread.currentThread().getName());
            return SynchronizedLazyLoadedSingleton.getInstance();
        };

        int count = 0;
        while(count < 10) {
            futures.add(executorService.submit(runnableTask));
            count++;
        }
        futures.forEach(e -> {
            try {
                setHoldingSingletonObj.add(e.get());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        executorService.shutdown();
        assertEquals(1, setHoldingSingletonObj.size());
    }

}
