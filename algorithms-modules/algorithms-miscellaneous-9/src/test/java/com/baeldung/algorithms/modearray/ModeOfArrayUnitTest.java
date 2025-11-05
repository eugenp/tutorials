package com.baeldung.algorithms.modearray;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

public class ModeOfArrayUnitTest {

    @Test
    public void givenArray_whenFindUsingFrequencyCount_thenOutputMode() {

        int[] nums = { 1, 2, 2, 3, 3, 4, 4, 4, 5 };
        Map<Integer, Integer> frequencyMap = new HashMap<>();

        for (int num : nums) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }

        int maxFrequency = 0;
        for (int frequency : frequencyMap.values()) {
            if (frequency > maxFrequency) {
                maxFrequency = frequency;
            }
        }

        Set<Integer> modes = new HashSet<>();
        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() == maxFrequency) {
                modes.add(entry.getKey());
            }
        }
        Set<Integer> expected = new HashSet<>(Arrays.asList(4));
        assertEquals(expected, modes);
    }

    @Test
    public void givenArray_whenFindUsingSorting_thenOutputMode() {
        int[] nums = { 1, 2, 2, 3, 3, 4, 4, 4, 5 };

        Arrays.sort(nums);

        int maxCount = 1;
        int currentCount = 1;
        Set<Integer> modes = new HashSet<>();

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1]) {
                currentCount++;
            } else {
                currentCount = 1;
            }

            if (currentCount > maxCount) {
                maxCount = currentCount;
                modes.clear();
                modes.add(nums[i]);
            } else if (currentCount == maxCount) {
                modes.add(nums[i]);
            }
        }

        if (nums.length == 1) {
            modes.add(nums[0]);
        }

        Set<Integer> expected = new HashSet<>(Arrays.asList(4));
        assertEquals(expected, modes);
    }

    @Test
    public void givenArray_whenFindUsingTreeMap_thenOutputMode() {
        int[] nums = { 1, 2, 2, 3, 3, 4, 4, 4, 5 };
        Map<Integer, Integer> frequencyMap = new TreeMap<>();

        for (int num : nums) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }

        int maxFrequency = 0;
        for (int frequency : frequencyMap.values()) {
            if (frequency > maxFrequency) {
                maxFrequency = frequency;
            }
        }

        Set<Integer> modes = new HashSet<>();
        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() == maxFrequency) {
                modes.add(entry.getKey());
            }
        }

        Set<Integer> expected = new HashSet<>(Arrays.asList(4));
        assertEquals(expected, modes);
    }

    @Test
    public void givenArray_whenFindUsingStream_thenOutputMode() {
        int[] nums = { 1, 2, 2, 3, 3, 4, 4, 4, 5 };
        Map<Integer, Long> frequencyMap = Arrays.stream(nums)
            .boxed()
            .collect(Collectors.groupingBy(e -> e, Collectors.counting()));

        long maxFrequency = Collections.max(frequencyMap.values());

        Set<Integer> modes = frequencyMap.entrySet()
            .stream()
            .filter(entry -> entry.getValue() == maxFrequency)
            .map(Map.Entry::getKey)
            .collect(Collectors.toSet());

        Set<Integer> expected = new HashSet<>(Arrays.asList(4));
        assertEquals(expected, modes);
    }
}
