package com.baeldung.java8;


import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.Assert.*;


public class Java8ExecutorServiceTest {

    private Runnable runnableTask;
    private Callable<String> callableTask;
    private List<Callable<String>> callableTasks;

    @Before
    public void init() {

        runnableTask = () -> {
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        callableTask = () -> {
            TimeUnit.MILLISECONDS.sleep(300);
            return "Task's execution";
        };

        callableTasks = new ArrayList<>();
        callableTasks.add(callableTask);
        callableTasks.add(callableTask);
        callableTasks.add(callableTask);
    }

    @Test
    public void creationSubmittingTaskShuttingDown_whenShutDown_thenCorrect() {

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.submit(runnableTask);
        executorService.submit(callableTask);
        executorService.shutdown();

        assertTrue(executorService.isShutdown());
    }

    @Test
    public void creationSubmittingTasksShuttingDownNow_whenShutDownAfterAwating_thenCorrect() {

        ExecutorService threadPoolExecutor =
                new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
                        new LinkedBlockingQueue<>());

        for (int i = 0; i < 100; i++) {
            threadPoolExecutor.submit(callableTask);
        }

        List<Runnable> notExecutedTasks = smartShutdown(threadPoolExecutor);

        assertTrue(threadPoolExecutor.isShutdown());
        assertFalse(notExecutedTasks.isEmpty());
        assertTrue(notExecutedTasks.size() > 0 && notExecutedTasks.size() < 98);
    }

    private List<Runnable> smartShutdown(ExecutorService executorService) {

        List<Runnable> notExecutedTasks = new ArrayList<>();
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                notExecutedTasks = executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            notExecutedTasks = executorService.shutdownNow();
        }
        return notExecutedTasks;
    }

    @Test
    public void submittingTasks_whenExecutedOneAndAll_thenCorrect() {

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        String result = null;
        List<Future<String>> futures = new ArrayList<>();
        try {
            result = executorService.invokeAny(callableTasks);
            futures = executorService.invokeAll(callableTasks);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        assertEquals("Task's execution", result);
        assertTrue(futures.size() == 3);
    }

    @Test
    public void submittingTaskShuttingDown_whenGetExpectedResult_thenCorrect() {

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Future<String> future = executorService.submit(callableTask);
        String result = null;
        try {
            result = future.get();
            result = future.get(200, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }

        executorService.shutdown();

        assertEquals("Task's execution", result);
    }

    @Test
    public void submittingTask_whenCanceled_thenCorrect() {

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Future<String> future = executorService.submit(callableTask);

        boolean canceled = future.cancel(true);
        boolean isCancelled = future.isCancelled();

        executorService.shutdown();

        assertTrue(canceled);
        assertTrue(isCancelled);
    }

    @Test
    public void submittingTaskScheduling_whenExecuted_thenCorrect() {

        ScheduledExecutorService executorService = Executors
                .newSingleThreadScheduledExecutor();

        Future<String> resultFuture = executorService.schedule(callableTask, 1, TimeUnit.SECONDS);
        String result = null;
        try {
            result = resultFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        executorService.shutdown();

        assertEquals("Task's execution", result);
    }
}
