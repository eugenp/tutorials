package com.baeldung.concurrent.futurevscompletablefuturevsrxjava;

import java.util.concurrent.Callable;

public class ObjectCallable implements Callable<TestObject> {
    @Override
    public TestObject call() throws Exception {
        return new TestObject();
    }
}
