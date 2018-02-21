package com.baeldung.concurrent.volatilekeyword;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

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
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            valueReadByThread2 = sharedObject.getCount();
        });
        readerOne.start();
        Thread.sleep(10);

        Thread readerTwo = new Thread(() -> {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            valueReadByThread3 = sharedObject.getCount();
        });
        readerTwo.start();
        Thread.sleep(10);

        assertThat(valueReadByThread2).isEqualTo(1);
        assertThat(valueReadByThread3).isEqualTo(1);

    }

    @Test
    public void whenTwoThreadWrites_thenVolatileReadsFromMainMemory() throws InterruptedException {
        Thread writerOne = new Thread(() -> sharedObject.increamentCount());
        writerOne.start();

        Thread writerTwo = new Thread(() -> sharedObject.increamentCount());
        writerTwo.start();
        Thread.sleep(10);

        Thread readerOne = new Thread(() -> valueReadByThread2 = sharedObject.getCount());
        readerOne.start();

        Thread readerTwo = new Thread(() -> valueReadByThread3 = sharedObject.getCount());
        readerTwo.start();

        assertThat(valueReadByThread2).isEqualTo(2);
        assertThat(valueReadByThread3).isEqualTo(2);

    }
}
