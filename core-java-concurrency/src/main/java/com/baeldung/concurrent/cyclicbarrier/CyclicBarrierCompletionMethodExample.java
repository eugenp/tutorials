package com.baeldung.concurrent.cyclicbarrier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierCompletionMethodExample {

    private int count;
    private int threadCount;
    private final List<String> outputScraper;
    
    CyclicBarrierCompletionMethodExample(final List<String> outputScraper, int count, int threadCount) {
        this.outputScraper = outputScraper;
        this.count = count;
        this.threadCount = threadCount;
    }

    public int countTrips() {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(count, () -> {
            outputScraper.add("Barrier is Tripped");
        });

        ExecutorService es = Executors.newFixedThreadPool(threadCount);
        for (int i = 0; i < threadCount; i++) {
            es.execute(() -> {
                try {
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
        CyclicBarrierCompletionMethodExample ex = new CyclicBarrierCompletionMethodExample(new ArrayList<String>(), 5, 20);
        System.out.println("Count : " + ex.countTrips());
    }
}
