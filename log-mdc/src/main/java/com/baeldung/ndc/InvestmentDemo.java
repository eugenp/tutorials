package com.baeldung.ndc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.baeldung.ndc.log4j2.Log4J2Runnable;

public class InvestmentDemo {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        InvestmentTransactionFactory transactionFactory = new InvestmentTransactionFactory();

        for (int i = 0; i < 10; i++) {
            Investment tx = transactionFactory.newInstance();

            // Runnable task = new Log4jNDCRunnable(tx);
            Runnable task = new Log4J2Runnable(tx);

            // JBoss Logging with JBoss LogManager as provider
            // Properties props = System.getProperties();
            // props.setProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager");
            // Runnable task = new JBossLoggingRunnable(tx);

            executor.submit(task);
        }
        executor.shutdown();
    }
}
