package com.baeldung.threadsstartatsametime;

import java.time.Instant;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class WorkerWithCyclicBarrier extends Thread {
    private CyclicBarrier barrier;

    public WorkerWithCyclicBarrier(String name, CyclicBarrier barrier) {
        this.barrier = barrier;
        this.setName(name);
    }

    @Override public void run() {
        try {
            System.out.printf("[ %s ] created, blocked by the barrier\n", getName());
            barrier.await();
            System.out.printf("[ %s ] starts at: %s\n", getName(), Instant.now());
            // do actual work here...
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
