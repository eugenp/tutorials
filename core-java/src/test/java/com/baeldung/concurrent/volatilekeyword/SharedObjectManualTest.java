package com.baeldung.concurrent.volatilekeyword;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class SharedObjectManualTest {

    SharedObject sharedObject;
    int valueReadByThread2;
    int valueReadByThread3;

    @Before
    public void setUp(){
        sharedObject = new SharedObject();
    }

    @Test
    public void whenOneThreadWrites_thenVolatileReadsFromMainMemory() throws InterruptedException {
        Thread writer = new Thread(){
            public void run(){
                sharedObject.increamentCount();
            }
        };
        writer.start();


        Thread readerOne = new Thread(){
            public void run(){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                valueReadByThread2= sharedObject.getCount();
            }
        };
        readerOne.start();

        Thread readerTwo = new Thread(){
            public void run(){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                valueReadByThread3=sharedObject.getCount();
            }
        };
        readerTwo.start();

        assertEquals(1,valueReadByThread2);
        assertEquals(1,valueReadByThread3);

    }

    @Test
    public void whenTwoThreadWrites_thenVolatileReadsFromMainMemory() throws InterruptedException {
        Thread writerOne = new Thread(){
            public void run(){
                sharedObject.increamentCount();
            }
        };
        writerOne.start();
        Thread.sleep(100);

        Thread writerTwo = new Thread(){
            public void run(){
                sharedObject.increamentCount();
            }
        };
        writerTwo.start();
        Thread.sleep(100);

        Thread readerOne = new Thread(){
            public void run(){
                valueReadByThread2= sharedObject.getCount();
            }
        };
        readerOne.start();

        Thread readerTwo = new Thread(){
            public void run(){
                valueReadByThread3=sharedObject.getCount();
            }
        };
        readerTwo.start();

        assertEquals(2,valueReadByThread2);
        assertEquals(2,valueReadByThread3);

    }
    @After
    public void cleanup(){
        sharedObject = null;
    }
}
