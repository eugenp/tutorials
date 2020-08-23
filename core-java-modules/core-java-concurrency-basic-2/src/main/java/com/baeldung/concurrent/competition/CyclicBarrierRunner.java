package com.baeldung.concurrent.competition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class CyclicBarrierRunner implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(CyclicBarrierRunner.class.getName());
    private final int number;
    private final CyclicBarrier barrier;

    public CyclicBarrierRunner(int number, CyclicBarrier barrier) {
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
            log.info("{} start race in {}ns and lasted {}ns", this, start, end - start);
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
