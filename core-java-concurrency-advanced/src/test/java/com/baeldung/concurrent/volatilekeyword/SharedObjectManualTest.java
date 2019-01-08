package com.baeldung.concurrent.volatilekeyword;

import org.junit.Test;

import static org.junit.Assert.*;

public class SharedObjectManualTest {

    @Test
    public void whenOneThreadWrites_thenVolatileReadsFromMainMemory() throws InterruptedException {
        SharedObject sharedObject = new SharedObject();

        Thread writer = new Thread(() -> sharedObject.increamentCount());
        writer.start();
        Thread.sleep(100);

        Thread readerOne = new Thread(() -> {
            int valueReadByThread2 = sharedObject.getCount();
            assertEquals(1, valueReadByThread2);
        });
        readerOne.start();

        Thread readerTwo = new Thread(() -> {
            int valueReadByThread3 = sharedObject.getCount();
            assertEquals(1, valueReadByThread3);
        });
        readerTwo.start();

    }

    @Test
    public void whenTwoThreadWrites_thenVolatileReadsFromMainMemory() throws InterruptedException {
        SharedObject sharedObject = new SharedObject();
        Thread writerOne = new Thread(() -> sharedObject.increamentCount());
        writerOne.start();
        Thread.sleep(100);

        Thread writerTwo = new Thread(() -> sharedObject.increamentCount());
        writerTwo.start();
        Thread.sleep(100);

        Thread readerOne = new Thread(() -> {
            int valueReadByThread2 = sharedObject.getCount();
            assertEquals(2, valueReadByThread2);
        });
        readerOne.start();

        Thread readerTwo = new Thread(() -> {
            int valueReadByThread3 = sharedObject.getCount();
            assertEquals(2, valueReadByThread3);
        });
        readerTwo.start();

    }
}
