package com.baeldung.benchmark;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class IntegerListFilterUnitTest {

    private IntegerListFilter integerListFilter;

    @Before
    public void init() {
        integerListFilter = new IntegerListFilter();
        integerListFilter.setup();
    }

    @Test
    public void whenBenchmarkIsExecute_thenJDKListsMustBeOfSameSize() {
        assertEquals(integerListFilter.jdkList().size(), integerListFilter.jdkListParallel().size());
    }

    @Test
    public void whenBenchmarkIsExecute_thenMutableListsMustBeOfSameSize() {
        assertEquals(integerListFilter.ecMutableList().size(), integerListFilter.ecMutableListParallel().size());
    }

    @Test
    public void whenBenchmarkIsExecute_thenPrimitiveListsMustBeOfSameSize() {
        assertEquals(integerListFilter.ecPrimitive().size(), integerListFilter.ecPrimitiveParallel().size());
    }
}
