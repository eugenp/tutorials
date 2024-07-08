package com.baeldung.executerservicecustomthreadname;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomThreadFactory implements ThreadFactory {
    private AtomicInteger threadNumber = new AtomicInteger(1);
    private String threadlNamePrefix = "";

    public CustomThreadFactory(String threadlNamePrefix) {
        this.threadlNamePrefix = threadlNamePrefix;
    }

    public Thread newThread(Runnable runnable) {
        return new Thread(runnable, threadlNamePrefix + threadNumber.getAndIncrement());
    }
}