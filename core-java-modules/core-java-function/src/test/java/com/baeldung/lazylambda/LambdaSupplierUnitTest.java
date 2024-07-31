package com.baeldung.lazylambda;

import java.util.function.Supplier;

import org.junit.Test;
import org.mockito.Mockito;

public class LambdaSupplierUnitTest {

    @Test
    public void whenCalledMultipleTimes_thenShouldBeCalledMultipleTimes() {
        @SuppressWarnings("unchecked") Supplier<String> mockedExpensiveFunction = Mockito.mock(Supplier.class);
        Mockito.when(mockedExpensiveFunction.get())
            .thenReturn("expensive call");
        LambdaSupplier<String> testee = new LambdaSupplier<>(mockedExpensiveFunction);
        Mockito.verify(mockedExpensiveFunction, Mockito.never())
            .get();
        testee.getData();
        testee.getData();
        Mockito.verify(mockedExpensiveFunction, Mockito.times(2))
            .get();
    }

}
