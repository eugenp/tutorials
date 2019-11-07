package com.baeldung.list;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Demo different approaches to get count of duplicated elements in an
 * arrayList
 */
public class DuplicatesCounter {

    public static <T> Map<T, Long> countByClassicalLoop(List<T> inputList) {
        Map<T, Long> resultMap = new HashMap<>();
        for (T element : inputList) {
            if (resultMap.containsKey(element)) {
                resultMap.put(element, resultMap.get(element) + 1L);
            } else {
                resultMap.put(element, 1L);
            }
        }
        return resultMap;
    }

    public static <T> Map<T, Long> countByClassicalLoopWithMapCompute(List<T> inputList) {
        Map<T, Long> resultMap = new HashMap<>();
        for (T element : inputList) {
            resultMap.compute(element, (k, v) -> v == null ? 1 : v + 1);
        }
        return resultMap;
    }

    public static <T> Map<T, Long> countByStreamToMap(List<T> inputList) {
        return inputList.stream().collect(Collectors.toMap(Function.identity(), v -> 1L, Long::sum));
    }

    public static <T> Map<T, Long> countByStreamGroupBy(List<T> inputList) {
        return inputList.stream().collect(Collectors.groupingBy(k -> k, Collectors.counting()));
    }
}
