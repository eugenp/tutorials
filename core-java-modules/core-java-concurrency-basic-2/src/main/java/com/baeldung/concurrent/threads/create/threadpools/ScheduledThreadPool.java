package com.baeldung.concurrent.threads.create.threadpools;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPool{
    public static void main(String[] args){
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(3);
        executorService.schedule(()->System.out.println("executing scheduled runnable task"), 5, TimeUnit.SECONDS);

        executorService.shutdown();
    }
}