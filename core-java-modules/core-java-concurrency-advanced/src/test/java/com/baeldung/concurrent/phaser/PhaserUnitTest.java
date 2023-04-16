package com.baeldung.concurrent.phaser;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

import static junit.framework.TestCase.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PhaserUnitTest {

    private static Logger log = LoggerFactory.getLogger(PhaserUnitTest.class);

    @Test
    public void givenPhaser_whenCoordinateWorksBetweenThreads_thenShouldCoordinateBetweenMultiplePhases() {
        //given
        ExecutorService executorService = Executors.newCachedThreadPool();
        Phaser ph = new Phaser(1);
        assertEquals(0, ph.getPhase());

        //when
        executorService.submit(new LongRunningAction("thread-1", ph));
        executorService.submit(new LongRunningAction("thread-2", ph));
        executorService.submit(new LongRunningAction("thread-3", ph));

        //then
        log.debug("Thread {} waiting for others", Thread.currentThread().getName());
        ph.arriveAndAwaitAdvance();
        log.debug("Thread {} proceeding in phase {}", Thread.currentThread().getName(), ph.getPhase());
        
        assertEquals(1, ph.getPhase());

        //and
        executorService.submit(new LongRunningAction("thread-4", ph));
        executorService.submit(new LongRunningAction("thread-5", ph));

        log.debug("Thread {} waiting for others", Thread.currentThread().getName());
        ph.arriveAndAwaitAdvance();
        log.debug("Thread {} proceeding in phase {}", Thread.currentThread().getName(), ph.getPhase());
        
        assertEquals(2, ph.getPhase());


        ph.arriveAndDeregister();
    }
}
