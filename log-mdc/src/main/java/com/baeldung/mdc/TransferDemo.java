package com.baeldung.mdc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.baeldung.mdc.slf4j.Slf4jRunnable;

public class TransferDemo {

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(3);
        TransactionFactory transactionFactory = new TransactionFactory();

        for (int i = 0; i < 10; i++) {
            Transfer tx = transactionFactory.newInstance();

            // Runnable task = new Log4JRunnable(tx);
            // Runnable task = new Log4J2Runnable(tx);
            Runnable task = new Slf4jRunnable(tx);

            // JBoss Logging with JBoss LogManager as provider
            // Properties props = System.getProperties();
            // props.setProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager");
            // Runnable task = new JBossLoggingRunnable(tx);

            executor.submit(task);
        }

        executor.shutdown();

    }
}
