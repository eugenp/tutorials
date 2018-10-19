package com.baeldung.concurrent.countdownlatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountdownLatchResetExample {

    private int count;
    private int threadCount;
    private final List<String> outputScraper;
    
    CountdownLatchResetExample(final List<String> outputScraper, int count, int threadCount) {
        this.outputScraper = outputScraper;
        this.count = count;
        this.threadCount = threadCount;
    }
    
    public int countWaits() {
        CountDownLatch countDownLatch = new CountDownLatch(count);
        ExecutorService es = Executors.newFixedThreadPool(threadCount);
        for (int i = 0; i < threadCount; i++) {
            es.execute(() -> {
                if (countDownLatch.getCount() > 0) {
                    outputScraper.add("Count Left : " + countDownLatch.getCount());
                }
                countDownLatch.countDown();
            });
        }
        
        es.shutdown();
        return outputScraper.size();
    }

    public static void main(String[] args) {
        CountdownLatchResetExample ex = new CountdownLatchResetExample(new ArrayList<>(),5,20);
        System.out.println("Count : " + ex.countWaits());
    }
}
