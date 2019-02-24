package com.baeldung.concurrent.threadsafety.callables;

import com.baeldung.concurrent.threadsafety.services.AtomicCounter;
import java.util.concurrent.Callable;

public class AtomicCounterCallable implements Callable<Integer> {

    private final AtomicCounter counter;
    
    public AtomicCounterCallable(AtomicCounter counter) {
        this.counter = counter;
    }

    @Override
    public Integer call() throws Exception {
        counter.incrementCounter();
        return counter.getCounter();
    }
}
