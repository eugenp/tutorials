package com.baeldung.lazylambda;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public class LazyLambdaThreadSafeSupplier<T> {

    private final Supplier<T> expensiveData;

    private final AtomicReference<T> data;

    public LazyLambdaThreadSafeSupplier(Supplier<T> expensiveData) {
        this.expensiveData = expensiveData;
        data = new AtomicReference<>();
    }

    public T getData() {
        if (data.get() == null) {
            synchronized (data) {
                if (data.get() == null) {
                    data.set(expensiveData.get());
                }
            }
        }

        return data.get();
    }

}
