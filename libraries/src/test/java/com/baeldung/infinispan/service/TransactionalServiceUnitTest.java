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

        assertThat(timeThis(() -> transactionalService.getQuickHowManyVisits()))
          .isGreaterThan(500).isLessThan(1000);
    }

}
