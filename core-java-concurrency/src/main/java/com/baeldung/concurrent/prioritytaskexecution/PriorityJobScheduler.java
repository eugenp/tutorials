package com.baeldung.concurrent.prioritytaskexecution;

import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public class PriorityJobScheduler {

    private ExecutorService priorityJobPoolExecutor;
    private ExecutorService priorityJobScheduler;
    private PriorityBlockingQueue<Runnable> priorityQueue;

    public PriorityJobScheduler(Integer poolSize, Integer queueSize) {
        priorityJobPoolExecutor = Executors.newFixedThreadPool(poolSize);
        Comparator<? super Job> jobComparator = Comparator.comparing(Job::getJobPriority);
        priorityQueue = new PriorityBlockingQueue<Runnable>(queueSize, 
          (Comparator<? super Runnable>) jobComparator);

        priorityJobScheduler = Executors.newSingleThreadExecutor();
        priorityJobScheduler.execute(()->{
            while (true) {
                try {
                    priorityJobPoolExecutor.execute(priorityQueue.take());
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
    }

    public void scheduleJob(Job job) {
        priorityQueue.add(job);
    }

    public int getQueuedTaskCount() {
        return priorityQueue.size();
    }

    protected void close(ExecutorService scheduler) {
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
        }
    }

    public void closeScheduler() {
        close(priorityJobPoolExecutor);
        close(priorityJobScheduler);
    }
}
