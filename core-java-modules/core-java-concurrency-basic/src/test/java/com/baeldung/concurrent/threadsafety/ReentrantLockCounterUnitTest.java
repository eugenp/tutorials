package com.baeldung.concurrent.threadsafety;

import com.baeldung.concurrent.threadsafety.callables.ReentrantLockCounterCallable;
import com.baeldung.concurrent.threadsafety.services.ReentrantLockCounter;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;

public class ReentrantLockCounterUnitTest {
    
    @Test
    public void whenCalledIncrementCounter_thenCorrect() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        ReentrantLockCounter counter = new ReentrantLockCounter();
        Future<Integer> future1 = (Future<Integer>) executorService.submit(new ReentrantLockCounterCallable(counter));
        Future<Integer> future2 = (Future<Integer>) executorService.submit(new ReentrantLockCounterCallable(counter));
        
        assertThat(future1.get()).isEqualTo(1);
        assertThat(future2.get()).isEqualTo(2);
    }
}
