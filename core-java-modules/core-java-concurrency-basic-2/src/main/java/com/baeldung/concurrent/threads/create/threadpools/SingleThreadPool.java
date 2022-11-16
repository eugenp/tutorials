package com.baeldung.concurrent.threads.create.threadpools;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SingleThreadPool{
    public static void main(String[] args){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        try{
            Future<String> result = executorService.submit(()-> "callable task executed");
            System.out.println(result.get());
        }catch(InterruptedException | ExecutionException e){
            e.printStackTrace();
        }

        executorService.shutdown();
    }
}