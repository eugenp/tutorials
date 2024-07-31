package com.baeldung.mdc.log4j2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.baeldung.mdc.TransactionFactory;
import com.baeldung.mdc.Transfer;
import com.baeldung.mdc.log4j.Log4JRunnable;
import com.baeldung.mdc.log4j2.Log4J2Runnable;
import com.baeldung.mdc.slf4j.Slf4jRunnable;

public class DemoIntegrationTest {

    @Test
    public void main() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        TransactionFactory transactionFactory = new TransactionFactory();
        for (int i = 0; i < 10; i++) {
            Transfer tx = transactionFactory.newInstance();
            Runnable task = new Log4J2Runnable(tx);
            executor.submit(task);
        }
        executor.shutdown();
        executor.awaitTermination(60, TimeUnit.SECONDS);
    }
}
