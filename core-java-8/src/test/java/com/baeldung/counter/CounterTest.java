package com.baeldung.counter;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

import org.junit.Test;

import com.baeldung.counter.CounterUtil.MutableInteger;

public class CounterTest {

    @Test
    public void whenMapWithWrapper_runsSuccessfully() {
        Map<String, Integer> counterMap = new HashMap<>();
        CounterUtil.mapWithWrapper(counterMap);

        assertEquals(3, counterMap.get("China")
            .intValue());
        assertEquals(2, counterMap.get("India")
            .intValue());
    }

    @Test
    public void whenMapWithLambda_runsSuccessfully() {
        Map<String, Long> counterMap = CounterUtil.mapWithLambda();

        assertEquals(3, counterMap.get("China")
            .intValue());
        assertEquals(2, counterMap.get("India")
            .intValue());
    }

    @Test
    public void whenMapWithMutableInteger_runsSuccessfully() {
        Map<String, MutableInteger> counterMap = new HashMap<>();
        CounterUtil.mapWithMutableInteger(counterMap);
        assertEquals(3, counterMap.get("China")
            .getCount());
        assertEquals(2, counterMap.get("India")
            .getCount());
    }

    @Test
    public void whenMapWithPrimitiveArray_runsSuccessfully() {
        Map<String, int[]> counterMap = new HashMap<>();
        CounterUtil.mapWithPrimitiveArray(counterMap);
        assertEquals(3, counterMap.get("China")[0]);
        assertEquals(2, counterMap.get("India")[0]);
    }
}
