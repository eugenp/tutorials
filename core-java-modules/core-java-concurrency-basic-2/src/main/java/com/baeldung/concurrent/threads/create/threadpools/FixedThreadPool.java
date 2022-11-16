package com.baeldung.concurrent.threads.create.threadpools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixedThreadPool{

    public static void main(String[] args){
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        executorService.submit(()->{
            System.out.println("submitted a runnable task to fixed thread pool");
            System.out.println(Thread.currentThread().getName()+" from pool is executing the task");
        });

        executorService.shutdown();
    }
}