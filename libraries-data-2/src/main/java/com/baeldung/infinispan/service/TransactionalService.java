package com.baeldung.infinispan.service;

import org.infinispan.Cache;
import org.springframework.util.StopWatch;

import javax.transaction.TransactionManager;

public class TransactionalService {

    private final Cache<String, Integer> transactionalCache;

    private static final String KEY = "key";

    public TransactionalService(Cache<String, Integer> transactionalCache) {
        this.transactionalCache = transactionalCache;

        transactionalCache.put(KEY, 0);
    }

    public Integer getQuickHowManyVisits() {
        try {
            TransactionManager tm = transactionalCache.getAdvancedCache().getTransactionManager();
            tm.begin();
            Integer howManyVisits = transactionalCache.get(KEY);
            howManyVisits++;
            System.out.println("Ill try to set HowManyVisits to " + howManyVisits);
            StopWatch watch = new StopWatch();
            watch.start();
            transactionalCache.put(KEY, howManyVisits);
            watch.stop();
            System.out.println("I was able to set HowManyVisits to " + howManyVisits + " after waiting " + watch.getTotalTimeSeconds() + " seconds");

            tm.commit();
            return howManyVisits;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void startBackgroundBatch() {
        try {
            TransactionManager tm = transactionalCache.getAdvancedCache().getTransactionManager();
            tm.begin();
            transactionalCache.put(KEY, 1000);
            System.out.println("HowManyVisits should now be 1000, " + "but we are holding the transaction");
            Thread.sleep(1000L);
            tm.rollback();
            System.out.println("The slow batch suffered a rollback");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
