package com.baeldung.executorservicetest;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MyThreadPoolExecutor extends ThreadPoolExecutor {

    CountDownLatch doneSignal = null;

    public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue,
        int jobsNumberToWaitFor) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        doneSignal = new CountDownLatch(jobsNumberToWaitFor);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        doneSignal.countDown();
    }

    public void waitDone() throws InterruptedException {
        doneSignal.await();
    }
}
