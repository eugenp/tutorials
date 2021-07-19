package com.baeldung.mdc;

import com.baeldung.mdc.pool.MdcAwareThreadPoolExecutor;
import com.baeldung.mdc.slf4j.Slf4jRunnable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;

import static java.util.concurrent.TimeUnit.MINUTES;

public class TransferDemo {

    public static void main(String[] args) {

        ExecutorService executor = new MdcAwareThreadPoolExecutor(3, 3, 0, MINUTES,
          new LinkedBlockingQueue<>(), Thread::new, new AbortPolicy());

        TransactionFactory transactionFactory = new TransactionFactory();

        for (int i = 0; i < 10; i++) {
            Transfer tx = transactionFactory.newInstance();

            // Runnable task = new Log4JRunnable(tx);
            // Runnable task = new Log4J2Runnable(tx);
            Runnable task = new Slf4jRunnable(tx);

            executor.submit(task);
        }

        executor.shutdown();

    }
}
