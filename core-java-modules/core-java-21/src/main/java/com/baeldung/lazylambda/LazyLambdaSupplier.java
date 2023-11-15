package com.baeldung.lazylambda;

import java.util.function.Supplier;

public class LazyLambdaSupplier<T> {

    private final Supplier<T> expensiveData;

    private T data;

    public LazyLambdaSupplier(Supplier<T> expensiveData) {
        this.expensiveData = expensiveData;
    }

    public T getData() {
        if (data != null) {
            return data;
        }
        return data = expensiveData.get();
    }

}
