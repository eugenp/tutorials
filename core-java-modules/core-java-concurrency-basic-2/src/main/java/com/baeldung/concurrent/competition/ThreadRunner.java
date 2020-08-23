package com.baeldung.concurrent.competition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ThreadRunner implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(ThreadRunner.class.getName());
    private final int number;

    public ThreadRunner(int number) {
        this.number = number;
    }

    @Override
    public void run() {
        try {
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
        return "ThreadRunner{" + "number=" + number + '}';
    }
}
