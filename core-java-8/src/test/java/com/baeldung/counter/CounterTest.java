package com.baeldung.counter;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

import org.apache.commons.collections4.multiset.HashMultiSet;
import org.junit.Test;

public class CounterTest {

    private final static String[] COUNTRY_NAMES = { "China", "Australia", "India", "USA", "USSR", "UK", "China", "France", "Poland", "Austria", "India", "USA", "Egypt", "China" };

    @Test
    public void whenMapWithWrapper_runsSuccessfully() {
        Map<String, Integer> counterMap = new HashMap<>();
        mapWithWrapper(counterMap);

        assertEquals(3, counterMap.get("China")
            .intValue());
        assertEquals(2, counterMap.get("India")
            .intValue());
    }

    private void mapWithWrapper(Map<String, Integer> counterMap) {
        for (String country : COUNTRY_NAMES) {
            Integer occuranceCount = counterMap.get(country);
            if (occuranceCount == null) {
                counterMap.put(country, 1);
            } else {
                counterMap.put(country, occuranceCount + 1);
            }
        }
    }

    @Test
    public void whenMapWithLambda_runsSuccessfully() {
        Map<String, Long> counterMap = mapWithLambda();

        assertEquals(3, counterMap.get("China")
            .intValue());
        assertEquals(2, counterMap.get("India")
            .intValue());
    }

    private Map<String, Long> mapWithLambda() {
        return Stream.of(COUNTRY_NAMES)
            .collect(Collectors.groupingBy(String::toString, Collectors.counting()));
    }

    @Test
    public void whenMapWithGuava_runsSuccessfully() {
        HashMultiSet<String> counterMultiSet = new HashMultiSet<>();
        mapWithGuava(counterMultiSet);
        assertEquals(3, counterMultiSet.getCount("China"));
        assertEquals(2, counterMultiSet.getCount("India"));
    }

    private void mapWithGuava(HashMultiSet<String> counterMultiSet) {
        for (String country : COUNTRY_NAMES) {
            counterMultiSet.add(country);
        }
    }

    private static class MutableInteger {
        int count;

        public MutableInteger(int count) {
            this.count = count;
        }

        public void increment() {
            this.count++;
        }

        public int getCount() {
            return this.count;
        }
    }

    @Test
    public void whenMapWithMutableInteger_runsSuccessfully() {
        Map<String, MutableInteger> counterMap = new HashMap<>();
        mapWithMutableInteger(counterMap);
        assertEquals(3, counterMap.get("China")
            .getCount());
        assertEquals(2, counterMap.get("India")
            .getCount());
    }

    private void mapWithMutableInteger(Map<String, MutableInteger> counterMap) {
        for (String country : COUNTRY_NAMES) {
            MutableInteger oldValue = counterMap.get(country);
            if (oldValue != null) {
                oldValue.increment();
            } else {
                counterMap.put(country, new MutableInteger(1));
            }
        }
    }

    @Test
    public void whenMapWithPrimitiveArray_runsSuccessfully() {
        Map<String, int[]> counterMap = new HashMap<>();
        mapWithPrimitiveArray(counterMap);
        assertEquals(3, counterMap.get("China")[0]);
        assertEquals(2, counterMap.get("India")[0]);
    }

    private void mapWithPrimitiveArray(Map<String, int[]> counterMap) {
        for (String country : COUNTRY_NAMES) {
            int[] oldCounter = counterMap.get(country);
            if (oldCounter != null) {
                oldCounter[0] += 1;
            } else {
                counterMap.put(country, new int[] { 1 });
            }
        }
    }

    @Test
    public void multipleImplWithStats_runsSuccessfully() {
        long endTime = 0;
        long duration = 0;

        // naive approach
        long startTime = System.nanoTime();
        Map<String, MutableInteger> mutableCounterMap = new HashMap<>();
        for (int i = 0; i < 1000000; i++)
            mapWithMutableInteger(mutableCounterMap);
        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("Mutable integer :  " + duration);

        startTime = System.nanoTime();
        Map<String, int[]> arrayCounterMap = new HashMap<>();
        for (int i = 0; i < 1000000; i++)
            mapWithPrimitiveArray(arrayCounterMap);
        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("Array :  " + duration);

        startTime = System.nanoTime();
        Map<String, Integer> counterMap = new HashMap<String, Integer>();
        for (int i = 0; i < 1000000; i++)
            mapWithWrapper(counterMap);
        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("Integer Wrapper :  " + duration);

        startTime = System.nanoTime();
        HashMultiSet<String> counterMultiSet = new HashMultiSet<>();
        for (int i = 0; i < 1000000; i++)
            mapWithGuava(counterMultiSet);
        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("HashMultiSet :  " + duration);

        startTime = System.nanoTime();
        for (int i = 0; i < 1000000; i++)
            mapWithLambda();
        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("LambdaMap :  " + duration);

    }
}
