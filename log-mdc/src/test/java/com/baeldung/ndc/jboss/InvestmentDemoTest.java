package com.baeldung.ndc.jboss;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.baeldung.ndc.Investment;
import com.baeldung.ndc.InvestmentTransactionFactory;

public class InvestmentDemoTest {

    @Test
    public void givenJBossLogger_whenNDCAdded_thenNDCInLog() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        InvestmentTransactionFactory transactionFactory = new InvestmentTransactionFactory();
        for (int i = 0; i < 10; i++) {
            Investment tx = transactionFactory.newInstance();
            Properties props = System.getProperties();
            props.setProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager");
            Runnable task = new JBossLoggingRunnable(tx);
            executor.submit(task);
        }
        executor.shutdown();
        executor.awaitTermination(60, TimeUnit.SECONDS);
    }
}
