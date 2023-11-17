package com.baeldung.lazylambda;

import java.util.function.Supplier;

public class LazyLambdaSupplier<T> extends LambdaSupplier<T> {

    private T data;

    public LazyLambdaSupplier(Supplier<T> expensiveData) {
        super(expensiveData);
    }

    @Override
    public T getData() {
        if (data != null) {
            return data;
        }
        return data = expensiveData.get();
    }

}
