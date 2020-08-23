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
            counter.countDown();
            long start = System.nanoTime();
            Thread.sleep(1000);
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
