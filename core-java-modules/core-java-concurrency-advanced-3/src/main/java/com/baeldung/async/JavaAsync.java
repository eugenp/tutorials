package com.baeldung.async;

import static com.ea.async.Async.await;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.google.common.util.concurrent.AsyncCallable;
import com.google.common.util.concurrent.Callables;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.jcabi.aspects.Async;
import com.jcabi.aspects.Loggable;

public class JavaAsync {

    static {
        com.ea.async.Async.init();
    }

    private static final ExecutorService threadpool = Executors.newCachedThreadPool();

    public static void main (String[] args) throws InterruptedException, ExecutionException {
        int number = 20;

        //Thread Example
        factorialUsingThread(number).start();

        //FutureTask Example
        Future<Long> futureTask = factorialUsingFutureTask(number);
        System.out.println("Factorial of " + number + " is: " + futureTask.get());

        // CompletableFuture Example
        Future<Long> completableFuture = factorialUsingCompletableFuture(number);
        System.out.println("Factorial of " + number + " is: " + completableFuture.get());

        // EA Async example
        System.out.println("Factorial of " + number + " is: " + factorialUsingEAAsync(number));

        // cactoos async example
        Future<Long> asyncFuture = factorialUsingCactoos(number);
        System.out.println("Factorial of " + number + " is: " + asyncFuture.get());

        // Guava example
        ListenableFuture<Long> guavaFuture = factorialUsingGuavaServiceSubmit(number);
        System.out.println("Factorial of " + number + " is: " + guavaFuture.get());

        ListenableFuture<Long> guavaFutures = factorialUsingGuavaFutures(number);
        System.out.println("Factorial of " + number + " is: " + guavaFutures.get());

        // @async jcabi-aspect example
        Future<Long> aspectFuture = factorialUsingJcabiAspect(number);
        System.out.println("Factorial of " + number + " is: " + aspectFuture.get());

    }

    /**
     * Finds factorial of a number
     * @param number
     * @return
     */
    public static long factorial(int number) {
        long result = 1; 
        for(int i=number;i>0;i--) {
            result *= i; 
        } 
        return result; 
    }

    /**
     * Finds factorial of a number using Thread
     * @param number
     * @return
     */
    @Loggable
    public static Thread factorialUsingThread(int number) {
        Thread newThread = new Thread(() -> {
            System.out.println("Factorial of " + number + " is: " + factorial(number));
        });

        return newThread;
    }

    /**
     * Finds factorial of a number using FutureTask
     * @param number
     * @return
     */
    @Loggable
    public static Future<Long> factorialUsingFutureTask(int number) {
        Future<Long> futureTask = threadpool.submit(() -> factorial(number)); 

        while (!futureTask.isDone()) { 
            System.out.println("FutureTask is not finished yet..."); 
        } 

        return futureTask;
    }

    /**
     * Finds factorial of a number using CompletableFuture
     * @param number
     * @return
     */
    @Loggable
    public static Future<Long> factorialUsingCompletableFuture(int number) {
        CompletableFuture<Long> completableFuture = CompletableFuture.supplyAsync(() -> factorial(number));
        return completableFuture;
    }

    /**
     * Finds factorial of a number using EA Async
     * @param number
     * @return
     */
    @Loggable
    public static long factorialUsingEAAsync(int number) {
        CompletableFuture<Long> completableFuture = CompletableFuture.supplyAsync(() -> factorial(number));
        long result = await(completableFuture);
        return result;
    }

    /**
     * Finds factorial of a number using Async of Cactoos
     * @param number
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Loggable
    public static Future<Long> factorialUsingCactoos(int number) throws InterruptedException, ExecutionException {
        org.cactoos.func.Async<Integer, Long> asyncFunction = new org.cactoos.func.Async<Integer, Long>(input -> factorial(input));
        Future<Long> asyncFuture = asyncFunction.apply(number);
        return asyncFuture;
    }

    /**
     * Finds factorial of a number using Guava's ListeningExecutorService.submit()
     * @param number
     * @return
     */
    @Loggable
    public static ListenableFuture<Long> factorialUsingGuavaServiceSubmit(int number) {
        ListeningExecutorService service = MoreExecutors.listeningDecorator(threadpool);
        ListenableFuture<Long> factorialFuture = (ListenableFuture<Long>) service.submit(()-> factorial(number));
        return factorialFuture;
    }

    /**
     * Finds factorial of a number using Guava's Futures.submitAsync()
     * @param number
     * @return
     */
    @Loggable
    public static ListenableFuture<Long> factorialUsingGuavaFutures(int number) {
        ListeningExecutorService service = MoreExecutors.listeningDecorator(threadpool);
        AsyncCallable<Long> asyncCallable = Callables.asAsyncCallable(new Callable<Long>() {
            public Long call() {
                return factorial(number);
            }
        }, service);
        return Futures.submitAsync(asyncCallable, service);
    }

    /**
     * Finds factorial of a number using @Async of jcabi-aspects
     * @param number
     * @return
     */
    @Async
    @Loggable
    public static Future<Long> factorialUsingJcabiAspect(int number) {
        Future<Long> factorialFuture = CompletableFuture.supplyAsync(() -> factorial(number));
        return factorialFuture;
    }

}
