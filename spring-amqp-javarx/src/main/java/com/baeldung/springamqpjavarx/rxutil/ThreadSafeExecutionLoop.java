package com.baeldung.springamqpjavarx.rxutil;

import java.util.concurrent.Executor;

public class ThreadSafeExecutionLoop {

    private final Executor executor;

    public ThreadSafeExecutionLoop() {
        this.executor = new ThreadSafeExecutor();
    }

    public Executor getExecutor() {
        return executor;
    }

    public void init() {
        new Thread(() -> {
            while (!Thread.interrupted()) {
                try {
                    ((ThreadSafeExecutor) executor)
                      .getTasks()
                      .take()
                      .run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
