package com.baeldung.derive4j.lazy;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.function.Supplier;

public class LazyRequestUnitTest {

    @Test
    public void givenLazyContstructedRequest_whenRequestIsReferenced_thenRequestIsLazilyContructed() {
        LazyRequestSupplier mockSupplier = Mockito.spy(new LazyRequestSupplier());

        LazyRequest request = LazyRequestImpl.lazy(() -> mockSupplier.get());
        Mockito.verify(mockSupplier, Mockito.times(0)).get();
        Assert.assertEquals(LazyRequestImpl.getPath(request), "http://test.com/get");
        Mockito.verify(mockSupplier, Mockito.times(1)).get();

    }

    class LazyRequestSupplier implements Supplier<LazyRequest> {
        @Override
        public LazyRequest get() {
            return LazyRequestImpl.GET("http://test.com/get");
        }
    }
}
