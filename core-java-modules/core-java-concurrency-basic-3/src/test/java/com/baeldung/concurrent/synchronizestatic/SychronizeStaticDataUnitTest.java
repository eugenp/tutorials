package com.baeldung.concurrent.synchronizestatic;

import org.junit.Test;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SychronizeStaticDataUnitTest
{
    private final Executor pool = Executors.newFixedThreadPool(4);

    @Test
    public void whenNotSynchronized_thenDataOutOfOrder() {
        int numberToTest = 100;

        for(int i = 0; i < numberToTest; i++) {
            int finalI = i;
            pool.execute(() ->
                         {
                             new com.baeldung.concurrent.synchronizestatic.none.Employee(finalI, "John", "Smith");
                         });
        }
    }

    @Test
    public void whenVolatile_thenDataInOrder() {
        int numberToTest = 100;

        for(int i = 0; i < numberToTest; i++) {
            int finalI = i;
            pool.execute(() ->
                         {
                             new com.baeldung.concurrent.synchronizestatic.volatilekeyword.Employee(finalI, "John", "Smith");
                         });
        }
    }

    @Test
    public void whenSynchronizedMethod_thenDataInOrder() {
        int numberToTest = 100;

        for(int i = 0; i < numberToTest; i++) {
            int finalI = i;
            pool.execute(() ->
                         {
                             new com.baeldung.concurrent.synchronizestatic.synchronizedmethod.Employee(finalI, "John", "Smith");
                         });
        }
    }

    @Test
    public void whenSynchronizedBlock_thenDataInOrder() {
        int numberToTest = 100;

        for(int i = 0; i < numberToTest; i++) {
            int finalI = i;
            pool.execute(() ->
                         {
                             new com.baeldung.concurrent.synchronizestatic.synchronizedblock.Employee(finalI, "John", "Smith");
                         });
        }
    }

    @Test
    public void whenAtomicInteger_thenDataInOrder() {
        int numberToTest = 100;

        for(int i = 0; i < numberToTest; i++) {
            int finalI = i;
            pool.execute(() ->
                         {
                             new com.baeldung.concurrent.synchronizestatic.atomicinteger.Employee(finalI, "John", "Smith");
                         });
        }
    }

    @Test
    public void whenReentrantLock_thenDataInOrder() {
        int numberToTest = 100;

        for(int i = 0; i < numberToTest; i++) {
            int finalI = i;
            pool.execute(() ->
                         {
                             new com.baeldung.concurrent.synchronizestatic.reentrantlock.Employee(finalI, "John", "Smith");
                         });
        }
    }
}
