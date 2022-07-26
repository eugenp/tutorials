package com.baeldung.exception.arrayindexoutofbounds;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.baeldung.exception.currentstacktrace.StackTraceUsingThreadDemo;
import com.baeldung.exception.currentstacktrace.StackTraceUsingThrowableDemo;

public class CurrentStacktraceDemoUnitTest {

    @Test
    public void whenElementIsFecthedUsingThread_thenCorrectMethodAndClassIsReturned() {
        StackTraceElement[] stackTrace = new StackTraceUsingThreadDemo().methodA();

        StackTraceElement elementZero = stackTrace[0];
        assertEquals("java.lang.Thread", elementZero.getClassName());
        assertEquals("getStackTrace", elementZero.getMethodName());

        StackTraceElement elementOne = stackTrace[1];
        assertEquals("com.baeldung.tutorials.StackTraceUsingThreadDemo", elementOne.getClassName());
        assertEquals("methodB", elementOne.getMethodName());

        StackTraceElement elementTwo = stackTrace[2];
        assertEquals("com.baeldung.tutorials.StackTraceUsingThreadDemo", elementTwo.getClassName());
        assertEquals("methodA", elementTwo.getMethodName());

        StackTraceElement elementThree = stackTrace[3];
        assertEquals("test.java.com.baeldung.tutorials.CurrentStacktraceDemoUnitTest", elementThree.getClassName());
        assertEquals("whenElementIsFecthedUsingThread_thenCorrectMethodAndClassIsReturned", elementThree.getMethodName());
    }

    @Test
    public void whenElementIsFecthedUsingThrowable_thenCorrectMethodAndClassIsReturned() {
        StackTraceElement[] stackTrace = new StackTraceUsingThrowableDemo().methodA();

        StackTraceElement elementZero = stackTrace[0];
        assertEquals("com.baeldung.tutorials.StackTraceUsingThrowableDemo", elementZero.getClassName());
        assertEquals("methodB", elementZero.getMethodName());

        StackTraceElement elementOne = stackTrace[1];
        assertEquals("com.baeldung.tutorials.StackTraceUsingThrowableDemo", elementOne.getClassName());
        assertEquals("methodA", elementOne.getMethodName());

        StackTraceElement elementThree = stackTrace[2];
        assertEquals("test.java.com.baeldung.tutorials.CurrentStacktraceDemoUnitTest", elementThree.getClassName());
        assertEquals("whenElementIsFecthedUsingThrowable_thenCorrectMethodAndClassIsReturned", elementThree.getMethodName());
    }
}
