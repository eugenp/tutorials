package com.baeldung.counter;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CounterUtil {

    private final static String[] COUNTRY_NAMES = { "China", "Australia", "India", "USA", "USSR", "UK", "China", "France", "Poland", "Austria", "India", "USA", "Egypt", "China" };

    public static void mapWithWrapper(Map<String, Integer> counterMap) {
        for (String country : COUNTRY_NAMES) {
            Integer occuranceCount = counterMap.get(country);
            if (occuranceCount == null) {
                counterMap.put(country, 1);
            } else {
                counterMap.put(country, occuranceCount + 1);
            }
        }
    }

    public static Map<String, Long> mapWithLambda() {
        return Stream.of(COUNTRY_NAMES)
            .parallel()
            .collect(Collectors.groupingBy(String::toString, Collectors.counting()));
    }

    public static class MutableInteger {
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

    public static void mapWithMutableInteger(Map<String, MutableInteger> counterMap) {
        for (String country : COUNTRY_NAMES) {
            MutableInteger oldValue = counterMap.get(country);
            if (oldValue != null) {
                oldValue.increment();
            } else {
                counterMap.put(country, new MutableInteger(1));
            }
        }
    }

    public static void mapWithPrimitiveArray(Map<String, int[]> counterMap) {
        for (String country : COUNTRY_NAMES) {
            int[] oldCounter = counterMap.get(country);
            if (oldCounter != null) {
                oldCounter[0] += 1;
            } else {
                counterMap.put(country, new int[] { 1 });
            }
        }
    }

}
