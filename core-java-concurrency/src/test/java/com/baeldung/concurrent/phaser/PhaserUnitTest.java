package com.baeldung.concurrent.phaser;

import static junit.framework.TestCase.assertEquals;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

import org.junit.Test;

public class PhaserUnitTest {

    @Test
    public void givenPhaser_whenCoordinateWorksBetweenThreads_thenShouldCoordinateBetweenMultiplePhases() {
        // given
        ExecutorService executorService = Executors.newCachedThreadPool();
        Phaser ph = new Phaser(1);
        assertEquals(0, ph.getPhase());
        // when
        executorService.submit(new LongRunningAction("thread-1", ph));
        executorService.submit(new LongRunningAction("thread-2", ph));
        executorService.submit(new LongRunningAction("thread-3", ph));
        // then
        ph.arriveAndAwaitAdvance();
        assertEquals(1, ph.getPhase());
        // and
        executorService.submit(new LongRunningAction("thread-4", ph));
        executorService.submit(new LongRunningAction("thread-5", ph));
        ph.arriveAndAwaitAdvance();
        assertEquals(2, ph.getPhase());

        ph.arriveAndDeregister();
    }
}
