package com.baeldung.concurrent.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class SquareCalculator {
    
    private final ExecutorService executor;
    
    public SquareCalculator(ExecutorService executor) {
        this.executor = executor;
    }
    
    public Future<Integer> calculate(Integer input) {        
        return executor.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(1000);
                return input * input;
            }
        });
    }
}
