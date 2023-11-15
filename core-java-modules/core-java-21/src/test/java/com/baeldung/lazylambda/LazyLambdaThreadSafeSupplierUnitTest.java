package com.baeldung.lazylambda;

import java.util.function.Supplier;

import org.junit.Test;
import org.mockito.Mockito;

public class LazyLambdaThreadSafeSupplierUnitTest {

    @Test
    public void whenCalledMultipleTimes_thenShouldBeCalledOnlyOnce() {
        @SuppressWarnings("unchecked") Supplier<String> mockedExpensiveFunction = Mockito.mock(Supplier.class);
        Mockito.when(mockedExpensiveFunction.get()).thenReturn("expensive call");
        LazyLambdaThreadSafeSupplier<String> testee = new LazyLambdaThreadSafeSupplier<>(mockedExpensiveFunction);
        Mockito.verify(mockedExpensiveFunction, Mockito.never()).get();
        testee.getData();
        testee.getData();
        testee.getData();
        testee.getData();
        Mockito.verify(mockedExpensiveFunction, Mockito.times(1)).get();
    }

}
