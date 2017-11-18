package com.stackify.threadpools;

import java.math.BigInteger;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stackify.models.Employee;
import com.stackify.services.EmployeeService;

public class ThreadsApplication {

    private static final Logger logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

    public static void main(String[] args) {
         testExecutor();
         testExecutorService();
         testScheduledExecutorService();
         testThreadPoolExecutor();
        testForkJoinPool();
    }

    private static EmployeeService employeeService = new EmployeeService();

    public static void testExecutor() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> System.out.println("Single thread pool test"));
    }

    public static void testExecutorService() {

        Employee employee = new Employee("John", 2000);

        ExecutorService executor = Executors.newFixedThreadPool(10);

        Callable<Double> callableTask = () -> {
            return employeeService.calculateBonus(employee);
        };
        Future<Double> future = executor.submit(callableTask);

        try {
            if (future.isDone()) {
                double result = future.get();
                System.out.println("Bonus is:" + result);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        executor.shutdown();
    }

    public static void testScheduledExecutorService() {
        Employee employee = new Employee("John", 2000);

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);

        Callable<Double> callableTask = () -> {
            return employeeService.calculateBonus(employee);
        };

        Future<Double> futureScheduled = executor.schedule(callableTask, 2, TimeUnit.MILLISECONDS);

        try {
            System.out.println("Bonus:" + futureScheduled.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        executor.scheduleAtFixedRate(() -> System.out.println("Fixed Rate Scheduled"), 2, 2000, TimeUnit.MILLISECONDS);
        executor.scheduleWithFixedDelay(() -> System.out.println("Fixed Delay Scheduled"), 2, 2000, TimeUnit.MILLISECONDS);
    }

    public static void testThreadPoolExecutor() {
        ThreadPoolExecutor fixedPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        ThreadPoolExecutor cachedPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

        ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 6, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        executor.setMaximumPoolSize(8);

        ScheduledThreadPoolExecutor scheduledExecutor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(5);
    }

    public static void testForkJoinPool() {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        logger.info("Thread Pool Created");
        BigInteger result = pool.invoke(new FactorialTask(100));
        System.out.println(result.toString());
    }
}
