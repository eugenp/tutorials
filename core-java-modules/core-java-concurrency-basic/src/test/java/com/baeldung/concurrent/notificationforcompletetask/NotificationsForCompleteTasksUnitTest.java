package com.baeldung.concurrent.notificationforcompletetask;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.junit.Test;

public class NotificationsForCompleteTasksUnitTest {

    @Test
    public void whenImplementingRunnable_thenReceiveNotificationOfCompletedTask() {
        Task task = new Task();
        Callback callback = new Callback();
        RunnableImpl runnableImpl = new RunnableImpl(task, callback, "ready for next task");
        runnableImpl.run();
    }

    @Test
    public void whenUsingCompletableFuture_thenReceiveNotificationOfCompletedTask() {
        Task task = new Task();
        Callback callback = new Callback();
        CompletableFuture.runAsync(task)
          .thenAccept(result -> callback.taskDone("completion details: " + result));
    }

    @Test
    public void whenUsingThreadPoolExecutor_thenReceiveNotificationOfCompletedTask(){
        Task task = new Task();
        Callback callback = new Callback();
        AlertingThreadPoolExecutor executor = new AlertingThreadPoolExecutor(callback);
        executor.submit(task);
    }

    @Test
    public void whenUsingFutureTask_thenReceiveNotificationOfCompletedTask(){
        Task task = new Task();
        Callback callback = new Callback();
        FutureTask<String> future = new AlertingFutureTask(task, callback);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(future);
    }

}
