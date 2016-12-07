package com.baeldung.ndc.log4j2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.baeldung.ndc.Investment;
import com.baeldung.ndc.InvestmentTransactionFactory;

public class InvestmentDemoTest {

    @Test
    public void givenLog4j2Logger_whenNDCAdded_thenNDCInLog() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        InvestmentTransactionFactory transactionFactory = new InvestmentTransactionFactory();
        for (int i = 0; i < 10; i++) {
            Investment tx = transactionFactory.newInstance();
            Runnable task = new Log4J2Runnable(tx);
            executor.submit(task);
        }
        executor.shutdown();
        executor.awaitTermination(60, TimeUnit.SECONDS);
    }
}
