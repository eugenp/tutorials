package com.baeldung.reflection.exception.invocationtarget;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvocationTargetUnitTest {

    private static final Logger LOG = LoggerFactory.getLogger(InvocationTargetUnitTest.class);

    @Test
    public void whenCallingMethodThrowsException_thenCorrect() throws Exception {

        InvocationTargetExample targetExample = new InvocationTargetExample();
        Method method = InvocationTargetExample.class.getMethod("divideByZeroExample");

        Exception exception = assertThrows(InvocationTargetException.class, () -> method.invoke(targetExample));
        LOG.error("InvocationTargetException Thrown:", exception);
    }

    @Test
    public void whenCallingMethodThrowsException_thenPrintUnderlyingException() {
        try {
            InvocationTargetExample targetExample = new InvocationTargetExample();
            Method method = InvocationTargetExample.class.getMethod("divideByZeroExample");
            method.invoke(targetExample);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof ArithmeticException) {
                // handle ArithmeticException
                LOG.error("InvocationTargetException caused by ArithmeticException:", cause);
            } else {
                // handle other exceptions
                LOG.error("The underlying exception of InvocationTargetException:", cause);
            }
        } catch (NoSuchMethodException | IllegalAccessException e) {
            // for simplicity, rethrow reflection exceptions as RuntimeException
            throw new RuntimeException(e);
        }
    }
}