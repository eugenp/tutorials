package com.baeldung.springamqpjavarx.rxutil;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadSafeExecutor implements Executor {

    final BlockingQueue<Runnable> tasks = new LinkedBlockingQueue<>();

    @Override
    public void execute(Runnable command) {
        tasks.add(command);
    }

    BlockingQueue<Runnable> getTasks() {
        return tasks;
    }
}
