package com.baeldung.concurrent.threadsafety.callables;

import com.baeldung.concurrent.threadsafety.services.Counter;
import java.util.concurrent.Callable;

public class CounterCallable implements Callable<Integer> {

    private final Counter counter;
    
    public CounterCallable(Counter counter) {
        this.counter = counter;
    }

    @Override
    public Integer call() throws Exception {
        counter.incrementCounter();
        return counter.getCounter();
    }
}
