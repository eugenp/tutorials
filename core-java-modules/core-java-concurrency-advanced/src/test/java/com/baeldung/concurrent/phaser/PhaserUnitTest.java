package com.baeldung.concurrent.phaser;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

import static junit.framework.TestCase.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PhaserUnitTest {

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
        System.out.println("Thread " + Thread.currentThread().getName() + " waiting for others");
        ph.arriveAndAwaitAdvance();
        System.out.println("Thread " + Thread.currentThread().getName() + " proceeding in phase " + ph.getPhase());
        
        assertEquals(1, ph.getPhase());

        //and
        executorService.submit(new LongRunningAction("thread-4", ph));
        executorService.submit(new LongRunningAction("thread-5", ph));
        
        System.out.println("Thread " + Thread.currentThread().getName() + " waiting for others");
        ph.arriveAndAwaitAdvance();
        System.out.println("Thread " + Thread.currentThread().getName() + " proceeding in phase " + ph.getPhase());
        
        assertEquals(2, ph.getPhase());


        ph.arriveAndDeregister();
    }
}
