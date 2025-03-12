package com.baeldung.streams;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import java.util.stream.Collectors;

public class NestedLoopsToStreamsConverter {

    public static List<int[]> getAllPairsImperative(List<Integer> list1, List<Integer> list2) {
        List<int[]> pairs = new ArrayList<>();
        for (Integer num1 : list1) {
            for (Integer num2 : list2) {
                pairs.add(new int[] { num1, num2 });
            }
        }
        return pairs;
    }

    public static List<int[]> getAllPairsStream(List<Integer> list1, List<Integer> list2) {
        return list1.stream()
            .flatMap(num1 -> list2.stream().map(num2 -> new int[] { num1, num2 }))
            .collect(Collectors.toList());
    }

    public static List<int[]> getFilteredPairsImperative(List<Integer> list1, List<Integer> list2) {
        List<int[]> pairs = new ArrayList<>();
        for (Integer num1 : list1) {
            for (Integer num2 : list2) {
                if (num1 + num2 > 7) {
                    pairs.add(new int[] { num1, num2 });
                }
            }
        }
        return pairs;
    }

    public static List<int[]> getFilteredPairsStream(List<Integer> list1, List<Integer> list2) {
        return list1.stream()
            .flatMap(num1 -> list2.stream().map(num2 -> new int[] { num1, num2 }))
            .filter(pair -> pair[0] + pair[1] > 7)
            .collect(Collectors.toList());
    }

    public static Optional<int[]> getFirstMatchingPairImperative(List<Integer> list1, List<Integer> list2) {
        for (Integer num1 : list1) {
            for (Integer num2 : list2) {
                if (num1 + num2 > 7) {
                    return Optional.of(new int[] { num1, num2 });
                }
            }
        }
        return Optional.empty();
    }

    public static Optional<int[]> getFirstMatchingPairStream(List<Integer> list1, List<Integer> list2) {
        return list1.stream()
            .flatMap(num1 -> list2.stream().map(num2 -> new int[] { num1, num2 }))
            .filter(pair -> pair[0] + pair[1] > 7)
            .findFirst();
    }

}
