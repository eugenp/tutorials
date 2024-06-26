package com.baeldung.listduplicate;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ListDuplicate {
    public List<Integer> listDuplicateUsingSet(List<Integer> list) {
        List<Integer> duplicates = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        for (Integer i : list) {
            if (set.contains(i)) {
                duplicates.add(i);
            } else {
                set.add(i);
            }
        }
        return duplicates;
    }

    public List<Integer> listDuplicateUsingMap(List<Integer> list) {
        List<Integer> duplicates = new ArrayList<>();
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (Integer number : list) {
            frequencyMap.put(number, frequencyMap.getOrDefault(number, 0) + 1);
        }
        for (int number : frequencyMap.keySet()) {
            if (frequencyMap.get(number) != 1) {
                duplicates.add(number);
            }
        }
        return duplicates;
    }

    public List<Integer> listDuplicateUsingFilterAndSetAdd(List<Integer> list) {
        Set<Integer> elements = new HashSet<Integer>();
        return list.stream()
          .filter(n -> !elements.add(n))
          .collect(Collectors.toList());
    }

    public List<Integer> listDuplicateUsingCollectionsFrequency(List<Integer> list) {
        List<Integer> duplicates = new ArrayList<>();
        Set<Integer> set = list.stream()
          .filter(i -> Collections.frequency(list, i) > 1)
          .collect(Collectors.toSet());
        duplicates.addAll(set);
        return duplicates;
    }

    public List<Integer> listDuplicateUsingMapAndCollectorsGroupingBy(List<Integer> list) {
        List<Integer> duplicates = new ArrayList<>();
        Set<Integer> set = list.stream()
          .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
          .entrySet()
          .stream()
          .filter(m -> m.getValue() > 1)
          .map(Map.Entry::getKey)
          .collect(Collectors.toSet());
        duplicates.addAll(set);
        return duplicates;
    }

    public static <T> Set<T> findDuplicateInArrayWithForLoop(T[] array) {
        Set<T> duplicates = new HashSet<>();
        Set<T> seen = new HashSet<>();
        for (T val : array) {
            if (!seen.add(val)) {
                duplicates.add(val);
            }
        }
        return duplicates;
    }

    public static <T> Set<T> findDuplicateInArrayWithStream(T[] array) {
        Set<T> seen = new HashSet<>();
        return Arrays.stream(array)
          .filter(val -> !seen.add(val))
          .collect(Collectors.toSet());
    }
}
