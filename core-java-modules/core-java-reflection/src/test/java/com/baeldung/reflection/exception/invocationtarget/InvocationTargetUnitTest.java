package com.baeldung.reflection.exception.invocationtarget;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

public class InvocationTargetUnitTest {

    @Test
    public void whenCallingMethodThrowsException_thenAssertCauseOfInvocationTargetException() throws Exception {

        InvocationTargetExample targetExample = new InvocationTargetExample();
        Method method = InvocationTargetExample.class.getMethod("byZeroExample");
        
        Exception exception = assertThrows(InvocationTargetException.class, () -> method.invoke(targetExample));
        
        assertEquals(ArithmeticException.class, exception.getCause().getClass());
    }
}
