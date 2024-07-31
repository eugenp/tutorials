package com.baeldung.concurrent.futurevscompletablefuturevsrxjava;

import java.util.function.Supplier;

public class ObjectSupplier implements Supplier<TestObject> {

    @Override
    public TestObject get() {
        return new TestObject();
    }
}
