package com.baeldung.concurrent.threadsafety;

import com.baeldung.concurrent.threadsafety.callables.ReentranReadWriteLockCounterCallable;
import com.baeldung.concurrent.threadsafety.services.ReentrantReadWriteLockCounter;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;

public class ReentrantReadWriteLockCounterUnitTest {
    
    @Test
    public void whenCalledIncrementCounter_thenCorrect() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        ReentrantReadWriteLockCounter counter = new ReentrantReadWriteLockCounter();
        Future<Integer> future1 = (Future<Integer>) executorService.submit(new  ReentranReadWriteLockCounterCallable(counter));
        Future<Integer> future2 = (Future<Integer>) executorService.submit(new  ReentranReadWriteLockCounterCallable(counter));

        assertThat(future2.get()).isEqualTo(2);
        assertThat(future1.get()).isEqualTo(1);
    }
    
}
