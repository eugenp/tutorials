package com.baeldung.concurrent.threadsafety.tests;

import com.baeldung.concurrent.threadsafety.callables.ReentranReadWriteLockCounterCallable;
import com.baeldung.concurrent.threadsafety.services.ReentrantReadWriteLockCounter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class ReentrantReadWriteLockCounterTest {
    
    @Test
    public void whenCalledIncrementCounter_thenCorrect() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        ReentrantReadWriteLockCounter counter = new ReentrantReadWriteLockCounter();
        Future<Integer> future1 = (Future<Integer>) executorService.submit(new  ReentranReadWriteLockCounterCallable(counter));
        Future<Integer> future2 = (Future<Integer>) executorService.submit(new  ReentranReadWriteLockCounterCallable(counter));
        
        assertThat(future1.get()).isEqualTo(1);
        assertThat(future2.get()).isEqualTo(2);
    }
    
}
