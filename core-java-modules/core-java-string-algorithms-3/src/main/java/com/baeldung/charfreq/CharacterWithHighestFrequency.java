package com.baeldung.charfreq;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CharacterWithHighestFrequency {
    public static Character byStream(String input) {
        return input.chars()
          .mapToObj(x -> (char) x)
          .collect(groupingBy(x -> x, counting()))
          .entrySet()
          .stream()
          .max(comparingByValue())
          .get()
          .getKey();
    }

    public static Set<Character> byMap(String input) {
        Map<Character, Integer> map = new HashMap<>();
        for (char c : input.toCharArray()) {
            map.compute(c, (character, count) -> count == null ? 1 : ++count);
        }
        int maxCount = map.values()
          .stream()
          .mapToInt(Integer::intValue)
          .max()
          .getAsInt();

        return map.keySet()
          .stream()
          .filter(c -> map.get(c) == maxCount)
          .collect(toSet());
    }

    public static Set<Character> byBucket(String input) {
        int[] buckets = new int[128];

        int maxCount = 0;
        for (char c : input.toCharArray()) {
            buckets[c]++;
            maxCount = Math.max(buckets[c], maxCount);
        }

        int finalMaxCount = maxCount;
        return IntStream.range(0, 128)
          .filter(c -> buckets[c] == finalMaxCount)
          .mapToObj(i -> (char) i)
          .collect(Collectors.toSet());
    }
}