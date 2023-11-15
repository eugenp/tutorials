package com.baeldung.lazylambda;

import java.util.function.Supplier;

public class LambdaSupplier<T> {

    private final Supplier<T> expensiveData;

    public LambdaSupplier(Supplier<T> expensiveData) {
        this.expensiveData = expensiveData;
    }

    public T getData() {
        return expensiveData.get();
    }
}
