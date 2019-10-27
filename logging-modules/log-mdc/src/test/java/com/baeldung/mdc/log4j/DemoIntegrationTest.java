package com.baeldung.mdc.log4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.baeldung.mdc.TransactionFactory;
import com.baeldung.mdc.Transfer;

public class DemoIntegrationTest {

    @Test
    public void main() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        TransactionFactory transactionFactory = new TransactionFactory();
        for (int i = 0; i < 10; i++) {
            Transfer tx = transactionFactory.newInstance();
            Runnable task = new Log4JRunnable(tx);
            executor.submit(task);
        }
        executor.shutdown();
        executor.awaitTermination(60, TimeUnit.SECONDS);
    }
}
