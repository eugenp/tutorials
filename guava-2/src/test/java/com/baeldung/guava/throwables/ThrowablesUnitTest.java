package com.baeldung.guava.throwables;

import com.google.common.base.Throwables;
import org.junit.Test;

import java.util.function.Supplier;

public class ThrowablesUnitTest {

    @Test(expected = RuntimeException.class)
    public void whenThrowable_shouldWrapItInRuntimeException() throws Exception {
        try {
            throwThrowable(Throwable::new);
        } catch (Throwable t) {
            Throwables.propagateIfPossible(t, Exception.class);
            throw new RuntimeException(t);
        }
    }

    @Test(expected = Error.class)
    public void whenError_shouldPropagateAsIs() throws Exception {
        try {
            throwThrowable(Error::new);
        } catch (Throwable t) {
            Throwables.propagateIfPossible(t, Exception.class);
            throw new RuntimeException(t);
        }
    }

    @Test(expected = Exception.class)
    public void whenException_shouldPropagateAsIs() throws Exception {
        try {
            throwThrowable(Exception::new);
        } catch (Throwable t) {
            Throwables.propagateIfPossible(t, Exception.class);
            throw new RuntimeException(t);
        }
    }

    private <T extends Throwable> void throwThrowable(Supplier<T> exceptionSupplier) throws Throwable {
        throw exceptionSupplier.get();
    }

}
