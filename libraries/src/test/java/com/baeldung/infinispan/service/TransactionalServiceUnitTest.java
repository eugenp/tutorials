package com.baeldung.infinispan.service;

import com.baeldung.infinispan.ConfigurationTest;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class TransactionalServiceUnitTest extends ConfigurationTest {

    @Test
    public void whenLockingAnEntry_thenItShouldBeInaccessible() throws InterruptedException {
        Runnable backGroundJob = () -> transactionalService.startBackgroundBatch();
        Thread backgroundThread = new Thread(backGroundJob);
        transactionalService.getQuickHowManyVisits();
        backgroundThread.start();
        Thread.sleep(100); //lets wait our thread warm up
        long milis = System.currentTimeMillis();
        transactionalService.getQuickHowManyVisits();
        long executionTime = System.currentTimeMillis() - milis;

        assertThat(executionTime).isGreaterThan(500).isLessThan(1000);
    }

}
