package com.baeldung.map.incrementmapkey;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.util.concurrent.AtomicLongMap;

public class IncrementMapValueWays {

    public Map<Character, Integer> charFrequencyUsingContainsKey(String sentence) {
        Map<Character, Integer> charMap = new HashMap<>();
        for (int c = 0; c < sentence.length(); c++) {
            int count = 0;
            if (charMap.containsKey(sentence.charAt(c))) {
                count = charMap.get(sentence.charAt(c));
            }
            charMap.put(sentence.charAt(c), count + 1);
        }
        return charMap;
    }

    public Map<Character, Integer> charFrequencyUsingGetOrDefault(String sentence) {
        Map<Character, Integer> charMap = new HashMap<>();
        for (int c = 0; c < sentence.length(); c++) {
            charMap.put(sentence.charAt(c), charMap.getOrDefault(sentence.charAt(c), 0) + 1);
        }
        return charMap;
    }

    public Map<Character, Integer> charFrequencyUsingMerge(String sentence) {
        Map<Character, Integer> charMap = new HashMap<>();
        for (int c = 0; c < sentence.length(); c++) {
            charMap.merge(sentence.charAt(c), 1, Integer::sum);
        }
        return charMap;
    }

    public Map<Character, Integer> charFrequencyUsingCompute(String sentence) {
        Map<Character, Integer> charMap = new HashMap<>();
        for (int c = 0; c < sentence.length(); c++) {
            charMap.compute(sentence.charAt(c), (key, value) -> (value == null) ? 1 : value + 1);
        }
        return charMap;
    }

    public Map<Character, Long> charFrequencyUsingAtomicMap(String sentence) {
        AtomicLongMap<Character> map = AtomicLongMap.create();
        for (int c = 0; c < sentence.length(); c++) {
            map.getAndIncrement(sentence.charAt(c));
        }
        return map.asMap();
    }

    public Map<Character, Integer> charFrequencyWithConcurrentMap(String sentence, Map<Character, Integer> charMap) {
        for (int c = 0; c < sentence.length(); c++) {
            charMap.compute(sentence.charAt(c), (key, value) -> (value == null) ? 1 : value + 1);
        }
        return charMap;
    }

    public Map<Character, AtomicInteger> charFrequencyWithGetAndIncrement(String sentence) {
        Map<Character, AtomicInteger> charMap = new HashMap<>();
        for (int c = 0; c < sentence.length(); c++) {
            charMap.putIfAbsent(sentence.charAt(c), new AtomicInteger(0));
            charMap.get(sentence.charAt(c))
              .incrementAndGet();
        }
        return charMap;
    }

    public Map<Character, AtomicInteger> charFrequencyWithGetAndIncrementComputeIfAbsent(String sentence) {
        Map<Character, AtomicInteger> charMap = new HashMap<>();
        for (int c = 0; c < sentence.length(); c++) {
            charMap.computeIfAbsent(sentence.charAt(c), k -> new AtomicInteger(0))
              .incrementAndGet();
        }
        return charMap;
    }
}
