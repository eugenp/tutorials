package com.baeldung.concurrent.notificationForCompleteTask;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AlertingThreadPoolExecutor extends ThreadPoolExecutor {

    private final CallbackInterface callback;

    public AlertingThreadPoolExecutor(CallbackInterface callback) {
        super(1, 1, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));
        this.callback = callback;
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        callback.taskDone("runnable details here");
    }
}
