package com.baeldung.concurrent.mutex;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

import com.baeldung.concurrent.mutex.SequenceGenerator;
import com.baeldung.concurrent.mutex.SequenceGeneratorUsingMonitor;
import com.baeldung.concurrent.mutex.SequenceGeneratorUsingReentrantLock;
import com.baeldung.concurrent.mutex.SequenceGeneratorUsingSemaphore;
import com.baeldung.concurrent.mutex.SequenceGeneratorUsingSynchronizedBlock;
import com.baeldung.concurrent.mutex.SequenceGeneratorUsingSynchronizedMethod;

public class MutexUnitTest {

    private final int RANGE = 30;

    @Test
    public void givenUnsafeSequenceGenerator_whenRaceCondition_thenUnexpectedBehavior() throws Exception {
        Set<Integer> uniqueSequences = getASetOFUniqueSequences(new SequenceGenerator());
        Assert.assertNotEquals(RANGE, uniqueSequences.size());
    }

    @Test
    public void givenSequenceGeneratorUsingSynchronizedMethod_whenRaceCondition_thenSuccess() throws Exception {
        Set<Integer> uniqueSequences = getASetOFUniqueSequences(new SequenceGeneratorUsingSynchronizedMethod());
        Assert.assertEquals(RANGE, uniqueSequences.size());
    }

    @Test
    public void givenSequenceGeneratorUsingSynchronizedBlock_whenRaceCondition_thenSuccess() throws Exception {
        Set<Integer> uniqueSequences = getASetOFUniqueSequences(new SequenceGeneratorUsingSynchronizedBlock());
        Assert.assertEquals(RANGE, uniqueSequences.size());
    }

    @Test
    public void givenSequenceGeneratorUsingReentrantLock_whenRaceCondition_thenSuccess() throws Exception {
        Set<Integer> uniqueSequences = getASetOFUniqueSequences(new SequenceGeneratorUsingReentrantLock());
        Assert.assertEquals(RANGE, uniqueSequences.size());
    }

    @Test
    public void givenSequenceGeneratorUsingSemaphore_whenRaceCondition_thenSuccess() throws Exception {
        Set<Integer> uniqueSequences = getASetOFUniqueSequences(new SequenceGeneratorUsingSemaphore());
        Assert.assertEquals(RANGE, uniqueSequences.size());
    }

    @Test
    public void givenSequenceGeneratorUsingMonitor_whenRaceCondition_thenSuccess() throws Exception {
        Set<Integer> uniqueSequences = getASetOFUniqueSequences(new SequenceGeneratorUsingMonitor());
        Assert.assertEquals(RANGE, uniqueSequences.size());
    }

    private Set<Integer> getASetOFUniqueSequences(SequenceGenerator generator) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        Set<Integer> uniqueSequences = new HashSet<>();
        List<Future<Integer>> futures = new ArrayList<>();

        for (int i = 0; i < RANGE; i++) {
            futures.add(executor.submit(generator::getNextSequence));
        }

        for (Future<Integer> future : futures) {
            uniqueSequences.add(future.get());
        }

        executor.awaitTermination(15, TimeUnit.SECONDS);
        executor.shutdown();

        return uniqueSequences;
    }

}
