package com.baeldung.concurrent.threads.create.threadpools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CachedThreadPool {
    public static void main(String[] args){
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(()->{
            System.out.println("submitted a runnable task to cached thread pool");
            System.out.println(Thread.currentThread().getName()+" from pool is executing the task");
        });

        executorService.shutdown();
    }
}