package com.baeldung.concurrent.competition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

class CountDownLatchRunner implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(CountDownLatchRunner.class.getName());
    private final int number;
    private final CountDownLatch counter;

    public CountDownLatchRunner(int number, CountDownLatch counter) {
        this.number = number;
        this.counter = counter;
    }

    @Override
    public void run() {
        try {
            Thread.sleep((long) (1000 * Math.random()));
            counter.await();
            long start = System.nanoTime();
            long end = System.nanoTime();
            log.info("{} start race in {}ns and lasted {}ns", this, start, end - start);
        } catch (InterruptedException e) {
            Thread
              .currentThread()
              .interrupt();
        }
    }

    @Override
    public String toString() {
        return "CountDownLatchRunner{" + "number=" + number + '}';
    }
}
