package com.baeldung.concurrent.threadsafety;

import com.baeldung.concurrent.threadsafety.callables.CounterCallable;
import com.baeldung.concurrent.threadsafety.services.Counter;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;

public class CounterUnitTest {

    @Test
    public void whenCalledIncrementCounter_thenCorrect() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Counter counter = new Counter();
        Future<Integer> future1 = (Future<Integer>) executorService.submit(new CounterCallable(counter));
        Future<Integer> future2 = (Future<Integer>) executorService.submit(new CounterCallable(counter));

        // Just to make sure both are completed
        future1.get();
        future2.get();

        assertThat(counter.getCounter()).isEqualTo(2);
    }
}
