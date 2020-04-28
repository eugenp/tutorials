package com.baeldung.concurrent;

import org.junit.Ignore;
import org.junit.Test;

import edu.umd.cs.mtc.MultithreadedTestCase;
import edu.umd.cs.mtc.TestFramework;

public class MyCounterMultithreadedTCUnitTest extends MultithreadedTestCase {

    private MyCounter counter;

    @Override
    public void initialize() {
        counter = new MyCounter();
    }

    public void thread1() throws InterruptedException {
        counter.increment();
    }

    public void thread2() throws InterruptedException {
        counter.increment();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void finish() {
    	assertEquals(2, counter.getCount());
    }

    @Ignore
    @Test
    public void testCounter() throws Throwable {
        TestFramework.runManyTimes(new MyCounterMultithreadedTCUnitTest(), 1000);
    }
}
