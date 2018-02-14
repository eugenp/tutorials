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
        System.out.println("1");
        Phaser ph = new Phaser(1);
        assertEquals(0, ph.getPhase());
        System.out.println("2");
        // when
        executorService.submit(new LongRunningAction("thread-1", ph));
        executorService.submit(new LongRunningAction("thread-2", ph));
        executorService.submit(new LongRunningAction("thread-3", ph));
        System.out.println("3");
        // then
        ph.arriveAndAwaitAdvance();
        assertEquals(1, ph.getPhase());
        System.out.println("4");
        // and
        executorService.submit(new LongRunningAction("thread-4", ph));
        executorService.submit(new LongRunningAction("thread-5", ph));
        ph.arriveAndAwaitAdvance();
        assertEquals(2, ph.getPhase());
        System.out.println("5");

        ph.arriveAndDeregister();
        System.out.println("6");
    }
}
