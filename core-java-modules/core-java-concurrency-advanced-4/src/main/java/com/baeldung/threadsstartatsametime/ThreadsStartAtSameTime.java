package com.baeldung.threadsstartatsametime;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class ThreadsStartAtSameTime {

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        usingCountDownLatch();

        Thread.sleep(30);

        usingCyclicBarrier();
    }

    private static void usingCountDownLatch() throws InterruptedException {
        System.out.println("===============================================");
        System.out.println("        >>> Using CountDownLatch <<<<");
        System.out.println("===============================================");

        CountDownLatch latch = new CountDownLatch(1);

        WorkerWithCountDownLatch worker1 = new WorkerWithCountDownLatch("Worker with latch 1", latch);
        WorkerWithCountDownLatch worker2 = new WorkerWithCountDownLatch("Worker with latch 2", latch);

        worker1.start();
        worker2.start();

        Thread.sleep(10);//simulation of some actual work

        System.out.println("-----------------------------------------------");
        System.out.println(" Now release the latch:");
        System.out.println("-----------------------------------------------");
        latch.countDown();
    }

    private static void usingCyclicBarrier() throws BrokenBarrierException, InterruptedException {
        System.out.println("\n===============================================");
        System.out.println("        >>> Using CyclicBarrier <<<<");
        System.out.println("===============================================");

        CyclicBarrier barrier = new CyclicBarrier(3);

        WorkerWithCyclicBarrier worker1 = new WorkerWithCyclicBarrier("Worker with barrier 1", barrier);
        WorkerWithCyclicBarrier worker2 = new WorkerWithCyclicBarrier("Worker with barrier 2", barrier);

        worker1.start();
        worker2.start();

        Thread.sleep(10);//simulation of some actual work

        System.out.println("-----------------------------------------------");
        System.out.println(" Now open the barrier:");
        System.out.println("-----------------------------------------------");
        barrier.await();
    }

}
