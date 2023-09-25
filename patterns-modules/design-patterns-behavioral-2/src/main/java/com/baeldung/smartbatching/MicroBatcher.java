package com.baeldung.smartbatching;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

/**
 * @author KPentaris
 * @date 07/06/2023
 * @project design-patterns-behavioral-2
 */
public class MicroBatcher {
    Queue<String> tasksQueue = new ConcurrentLinkedQueue<>();
    Thread batchThread;
    int executionThreshold;
    int timeoutThreshold;
    boolean working = false;

    MicroBatcher(int executionThreshold, int timeoutThreshold, Consumer<List<String>> executionLogic) {
        batchThread = new Thread(batchHandling(executionLogic));
        batchThread.setDaemon(true);
        batchThread.start();
        this.executionThreshold = executionThreshold;
        this.timeoutThreshold = timeoutThreshold;
    }

    void submit(String task) {
        tasksQueue.add(task);
    }

    Runnable batchHandling(Consumer<List<String>> executionLogic) {
        return () -> {
            while (!batchThread.isInterrupted()) {
                long startTime = System.currentTimeMillis();
                while (tasksQueue.size() < executionThreshold && (System.currentTimeMillis() - startTime) < timeoutThreshold) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        return; // exit the external loop
                    }
                }
                List<String> tasks = new ArrayList<>(executionThreshold);
                while (tasksQueue.size() > 0 && tasks.size() < executionThreshold) {
                    tasks.add(tasksQueue.poll());
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
