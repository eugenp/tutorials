package com.baeldung.mdc.jboss;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.baeldung.mdc.TransactionFactory;
import com.baeldung.mdc.Transfer;

public class Demo {

    @Test
    public void givenJBossLogger_whenMDCAndNDCAdded_thenMDCAndNDCToLog() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        TransactionFactory transactionFactory = new TransactionFactory();
        for (int i = 0; i < 10; i++) {
            Transfer tx = transactionFactory.newInstance();
            Properties props = System.getProperties();
            props.setProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager");
            Runnable task = new JBossLoggingRunnable(tx);
            executor.submit(task);
        }
        executor.shutdown();
        executor.awaitTermination(60, TimeUnit.SECONDS);
    }
}
