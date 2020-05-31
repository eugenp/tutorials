package com.baeldung.guava.listenablefuture;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class LongRunningOperation implements Callable<Integer> {

    private final Integer result;

    public LongRunningOperation(Integer result) {
        this.result = result;
    }

    @Override
    public Integer call() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        return result;
    }
}
