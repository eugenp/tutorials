package com.baeldung.java.currentmethod;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * The class presents various ways of finding the name of currently executed method.
 */
public class CurrentlyExecutedMethodFinderUnitTest {

    @Test
    public void givenCurrentThread_whenGetStackTrace_thenFindMethod() {
        final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        assertEquals("givenCurrentThread_whenGetStackTrace_thenFindMethod", stackTrace[1].getMethodName());
    }

    @Test
    public void givenException_whenGetStackTrace_thenFindMethod() {
        String methodName = new Exception().getStackTrace()[0].getMethodName();
        assertEquals("givenException_whenGetStackTrace_thenFindMethod", methodName);
    }

    @Test
    public void givenThrowable_whenGetStacktrace_thenFindMethod() {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        assertEquals("givenThrowable_whenGetStacktrace_thenFindMethod", stackTrace[0].getMethodName());
    }

    @Test
    public void givenObject_whenGetEnclosingMethod_thenFindMethod() {
        String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
        assertEquals("givenObject_whenGetEnclosingMethod_thenFindMethod", methodName);
    }

    @Test
    public void givenLocal_whenGetEnclosingMethod_thenFindMethod() {
        class Local {};
        String methodName = Local.class.getEnclosingMethod().getName();
        assertEquals("givenLocal_whenGetEnclosingMethod_thenFindMethod", methodName);
    }

}
