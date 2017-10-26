package com.baeldung.concurrent.volatilekeyword;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class SharedObjectManualTest {

    private SharedObject sharedObject;
    private int valueReadByThread2;
    private int valueReadByThread3;

    @Before
    public void setUp() {
        sharedObject = new SharedObject();
    }

    @Test
    public void whenOneThreadWrites_thenVolatileReadsFromMainMemory() throws InterruptedException {
        Thread writer = new Thread(() -> sharedObject.increamentCount());
        writer.start();


        Thread readerOne = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            valueReadByThread2 = sharedObject.getCount();
        });
        readerOne.start();

        Thread readerTwo = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            valueReadByThread3 = sharedObject.getCount();
        });
        readerTwo.start();

        assertEquals(1, valueReadByThread2);
        assertEquals(1, valueReadByThread3);

    }

    @Test
    public void whenTwoThreadWrites_thenVolatileReadsFromMainMemory() throws InterruptedException {
        Thread writerOne = new Thread(() -> sharedObject.increamentCount());
        writerOne.start();
        Thread.sleep(100);

        Thread writerTwo = new Thread(() -> sharedObject.increamentCount());
        writerTwo.start();
        Thread.sleep(100);

        Thread readerOne = new Thread(() -> valueReadByThread2 = sharedObject.getCount());
        readerOne.start();

        Thread readerTwo = new Thread(() -> valueReadByThread3 = sharedObject.getCount());
        readerTwo.start();

        assertEquals(2, valueReadByThread2);
        assertEquals(2, valueReadByThread3);

    }
}
