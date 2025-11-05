package com.baeldung.generics;

import java.util.function.Supplier;

public class SenderServiceSupplier<T extends Sender> {

    private final Supplier<T> supplier;

    public SenderServiceSupplier(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public T createInstance() {
        return supplier.get();
    }
}
