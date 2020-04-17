package com.baeldung.concurrent.threadsafety;

import com.baeldung.concurrent.threadsafety.callables.ExtrinsicLockCounterCallable;
import com.baeldung.concurrent.threadsafety.services.ObjectLockCounter;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;

public class ObjectLockCounterUnitTest {

    @Test
    public void whenCalledIncrementCounter_thenCorrect() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        ObjectLockCounter counter = new ObjectLockCounter();
        Future<Integer> future1 = (Future<Integer>) executorService.submit(new ExtrinsicLockCounterCallable(counter));
        Future<Integer> future2 = (Future<Integer>) executorService.submit(new ExtrinsicLockCounterCallable(counter));

        // Just to make sure both are completed
        future1.get();
        future2.get();

        assertThat(counter.getCounter()).isEqualTo(2);
    }
}
