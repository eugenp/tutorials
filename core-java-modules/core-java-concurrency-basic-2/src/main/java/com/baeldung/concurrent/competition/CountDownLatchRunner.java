package com.baeldung.concurrent.competition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

import static java.util.concurrent.TimeUnit.MICROSECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

class CountDownLatchRunner extends Thread {
    private static final Logger log = LoggerFactory.getLogger(CountDownLatchRunner.class.getName());
    private String name;
    private int number;
    private CountDownLatch counter;

    public CountDownLatchRunner(String name, int number, CountDownLatch counter) {
        this.name = name;
        this.number = number;
        this.counter = counter;
    }

    @Override
    public void run() {
        try {
            counter.await();
            long start = System.nanoTime();
            Thread.sleep(1000);
            long end = System.nanoTime();
            log.info("{} start race in {}us and finish in {}us}", this, MICROSECONDS.convert(start, NANOSECONDS), MICROSECONDS.convert(end - start, NANOSECONDS));
        } catch (InterruptedException e) {
            Thread
              .currentThread()
              .interrupt();
        }
    }

    @Override
    public String toString() {
        return "Runner{" + "name='" + name + '\'' + ", number=" + number + '}';
    }
}
