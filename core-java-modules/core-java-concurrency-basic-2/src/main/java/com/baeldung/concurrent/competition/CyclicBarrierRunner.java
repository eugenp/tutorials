package com.baeldung.concurrent.competition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import static java.util.concurrent.TimeUnit.MICROSECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

class CyclicBarrierRunner extends Thread {
    private static final Logger log = LoggerFactory.getLogger(CyclicBarrierRunner.class.getName());
    private int number;
    private CyclicBarrier barrier;

    public CyclicBarrierRunner(String name, int number, CyclicBarrier barrier) {
        super(name);
        this.number = number;
        this.barrier = barrier;
    }

    @Override
    public void run() {
        try {
            barrier.await();
            long start = System.nanoTime();
            Thread.sleep(1000);
            long end = System.nanoTime();
            log.info("{} start race in {}us and finish in {}us}", this, MICROSECONDS.convert(start, NANOSECONDS), MICROSECONDS.convert(end - start, NANOSECONDS));
        } catch (InterruptedException | BrokenBarrierException e) {
            Thread
              .currentThread()
              .interrupt();
        }
    }

    @Override
    public String toString() {
        return "CyclicBarrierRunner{" + "number=" + number + '}';
    }
}
