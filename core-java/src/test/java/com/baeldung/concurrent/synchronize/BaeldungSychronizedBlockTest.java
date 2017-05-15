package com.baeldung.concurrent.synchronize;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.junit.Test;

public class BaeldungSychronizedBlockTest {

    @Test
    public void givenMultiThread_whenBlockSync() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(3);
        BaeldungSynchronizedBlocks synchronizedBlocks = new BaeldungSynchronizedBlocks();

        IntStream.range(0, 1000)
            .forEach(count -> service.submit(synchronizedBlocks::performSynchronisedTask));
        service.awaitTermination(100, TimeUnit.MILLISECONDS);

        assertEquals(1000, synchronizedBlocks.getCount());
    }

    @Test
    public void givenMultiThread_whenStaticSyncBlock() throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();

        IntStream.range(0, 1000)
            .forEach(count -> service.submit(BaeldungSynchronizedBlocks::performStaticSyncTask));
        service.awaitTermination(100, TimeUnit.MILLISECONDS);

        assertEquals(1000, BaeldungSynchronizedBlocks.getStaticCount());
    }

}
