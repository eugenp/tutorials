package com.baeldung.counter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.baeldung.counter.CounterUtil.MutableInteger;

class CounterUnitTest {

    @Test
    void whenMapWithWrapperAsCounter_runsSuccessfully() {
        Map<String, Integer> counterMap = new HashMap<>();
        CounterUtil.counterWithWrapperObject(counterMap);

        assertEquals(3, counterMap.get("China")
            .intValue());
        assertEquals(2, counterMap.get("India")
            .intValue());
    }

    @Test
    void whenMapWithLambdaAndWrapperCounter_runsSuccessfully() {
        Map<String, Long> counterMap = new HashMap<>();
        CounterUtil.counterWithLambdaAndWrapper(counterMap);

        assertEquals(3l, counterMap.get("China")
            .longValue());
        assertEquals(2l, counterMap.get("India")
            .longValue());
    }

    @Test
    void whenMapWithMutableIntegerCounter_runsSuccessfully() {
        Map<String, MutableInteger> counterMap = new HashMap<>();
        CounterUtil.counterWithMutableInteger(counterMap);
        assertEquals(3, counterMap.get("China")
            .getCount());
        assertEquals(2, counterMap.get("India")
            .getCount());
    }

    @Test
    void whenMapWithPrimitiveArray_runsSuccessfully() {
        Map<String, int[]> counterMap = new HashMap<>();
        CounterUtil.counterWithPrimitiveArray(counterMap);
        assertEquals(3, counterMap.get("China")[0]);
        assertEquals(2, counterMap.get("India")[0]);
    }
}
