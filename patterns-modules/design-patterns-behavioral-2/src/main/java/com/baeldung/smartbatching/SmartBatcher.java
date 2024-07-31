package com.baeldung.smartbatching;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

/**
 * @author KPentaris
 * @date 07/06/2023
 * @project design-patterns-behavioral-2
 */
public class SmartBatcher {
    BlockingQueue<String> tasksQueue = new LinkedBlockingQueue<>();
    Thread batchThread;
    int executionThreshold;
    boolean working = false;

    SmartBatcher(int executionThreshold, Consumer<List<String>> executionLogic) {
        batchThread = new Thread(batchHandling(executionLogic));
        batchThread.setDaemon(true);
        batchThread.start();
        this.executionThreshold = executionThreshold;
    }

    void submit(String task) {
        tasksQueue.add(task);
    }

    Runnable batchHandling(Consumer<List<String>> executionLogic) {
        return () -> {
            while (!batchThread.isInterrupted()) {
                List<String> tasks = new ArrayList<>(executionThreshold);
                while (tasksQueue.drainTo(tasks, executionThreshold) == 0) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        return; // exit the external loop
                    }
                }
                working = true;
                executionLogic.accept(tasks);
                working = false;
            }
        };
    }

    boolean finished() {
        return tasksQueue.isEmpty() && !working;
    }
}
