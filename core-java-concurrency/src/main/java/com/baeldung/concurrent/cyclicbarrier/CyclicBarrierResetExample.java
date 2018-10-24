package com.baeldung.concurrent.cyclicbarrier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierResetExample {

    private int count;
    private int threadCount;
    private final List<String> outputScraper;

    CyclicBarrierResetExample(final List<String> outputScraper, int count, int threadCount) {
        this.outputScraper = outputScraper;
        this.count = count;
        this.threadCount = threadCount;
    }

    public int countWaits() {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(count);

        ExecutorService es = Executors.newFixedThreadPool(threadCount);
        for (int i = 0; i < threadCount; i++) {
            es.execute(() -> {
                try {
                    if (cyclicBarrier.getNumberWaiting() > 0) {
                        outputScraper.add("Count Updated");
                    }   
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
        es.shutdown();
        return outputScraper.size();
    }

    public static void main(String[] args) {
        CyclicBarrierResetExample ex = new CyclicBarrierResetExample(new ArrayList<String>(), 7, 20);
        System.out.println("Count : " + ex.countWaits());
    }
}
