package com.baeldung.concurrent;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

import com.google.testing.threadtester.AnnotatedTestRunner;
import com.google.testing.threadtester.ThreadedAfter;
import com.google.testing.threadtester.ThreadedBefore;
import com.google.testing.threadtester.ThreadedMain;
import com.google.testing.threadtester.ThreadedSecondary;

public class MyCounterThreadWeaverUnitTest {

    private MyCounter counter;

    @ThreadedBefore
    public void before() {
        counter = new MyCounter();
    }

    @ThreadedMain
    public void mainThread() {
        counter.increment();
    }

    @ThreadedSecondary
    public void secondThread() {
        counter.increment();
    }

    @ThreadedAfter
    public void after() {
        assertEquals(2, counter.getCount());
    }

    @Ignore
    @Test
    public void testCounter() {
        new AnnotatedTestRunner().runTests(this.getClass(), MyCounter.class);
    }

}