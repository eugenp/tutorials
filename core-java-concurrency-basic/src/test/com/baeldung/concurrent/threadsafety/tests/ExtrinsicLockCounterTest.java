package com.baeldung.concurrent.threadsafety.tests;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.baeldung.concurrent.threadsafety.callables.ExtrinsicLockCounterCallable;
import com.baeldung.concurrent.threadsafety.services.ExtrinsicLockCounter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExtrinsicLockCounterTest {

    @Test
    public void whenCalledIncrementCounter_thenCorrect() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        ExtrinsicLockCounter counter = new ExtrinsicLockCounter();
        Future<Integer> future1 = (Future<Integer>) executorService.submit(new ExtrinsicLockCounterCallable(counter));
        Future<Integer> future2 = (Future<Integer>) executorService.submit(new ExtrinsicLockCounterCallable(counter));
        
        assertThat(future1.get()).isEqualTo(1);
        assertThat(future2.get()).isEqualTo(2);
    }
}
