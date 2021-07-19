package com.baeldung.benchmark;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class IntegerListSumUnitTest {

    private IntegerListSum integerListSum;

    @Before
    public void init() {
        integerListSum = new IntegerListSum();
        integerListSum.setup();
    }

    @Test
    public void whenBenchmarkIsExecute_thenJDKListsMustHaveSameValue() {
        assertEquals(integerListSum.jdkList(), integerListSum.jdkListParallel());
    }

    @Test
    public void whenBenchmarkIsExecute_thenMutableListsMustHaveSameValue() {
        assertEquals(integerListSum.ecMutableList(), integerListSum.ecMutableListParallel());
    }

    @Test
    public void whenBenchmarkIsExecute_thenPrimitiveListsMustHaveSameValue() {
        assertEquals(integerListSum.ecPrimitive(), integerListSum.ecPrimitiveParallel());
    }
}
