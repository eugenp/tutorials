package com.baeldung.concurrent.competition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.concurrent.TimeUnit.MICROSECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

class ThreadRunner extends Thread {
    private static final Logger log = LoggerFactory.getLogger(ThreadRunner.class.getName());
    private String name;
    private int number;

    public ThreadRunner(String name, int number) {
        this.name = name;
        this.number = number;
    }

    @Override
    public void run() {
        try {
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
