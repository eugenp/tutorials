package com.baeldung.array.reversearray;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ReverseArrayElements {

    public static void reverseRowsUsingSimpleForLoops(int[][] array) {
        for (int row = 0; row < array.length; row++) {
            for (int col = 0; col < array[row].length / 2; col++) {
                int current = array[row][col];
                array[row][col] = array[row][array[row].length - col - 1];
                array[row][array[row].length - col - 1] = current;
            }
        }
    }

    public static void reverseRowsUsingNestedIntStreams(int[][] array) {
        IntStream.range(0, array.length)
            .forEach(row -> IntStream.range(0, array[row].length / 2)
                .forEach(col -> {
                    int current = array[row][col];
                    array[row][col] = array[row][array[row].length - col - 1];
                    array[row][array[row].length - col - 1] = current;
                }));
    }

    public static void reverseRowsUsingCollectionsReverse(int[][] array) {
        for (int row = 0; row < array.length; row++) {
            List<Integer> collectionBoxedRow = Arrays.stream(array[row])
                .boxed()
                .collect(Collectors.toList());

            Collections.reverse(collectionBoxedRow);

            array[row] = collectionBoxedRow.stream()
                .mapToInt(Integer::intValue)
                .toArray();
        }
    }

    public static void reverseRowsUsingCollectionsReverse(List<List<Integer>> array) {
        array.forEach(Collections::reverse);
    }

    public static Stream<Iterable<Iterable<Integer>>> reverse(List<List<Integer>> list) {
        return list.stream()
            .map(row -> row.stream()
                .collect(Collector.of(ArrayDeque::new, ArrayDeque::addFirst, (d1, d2) -> {
                    d2.addAll(d1);
                    return d2;
                })));
    }

    public static void reverseColumns(int[][] array) {
        for (int col = 0; col < array[0].length; col++) {
            for (int row = 0; row < array.length / 2; row++) {
                int current = array[row][col];
                array[row][col] = array[array.length - row - 1][col];
                array[array.length - row - 1][col] = current;
            }
        }
    }

    public static void reverseColumnsUsingStreams(int[][] array) {
        IntStream.range(0, array[0].length)
            .forEach(col -> IntStream.range(0, array.length / 2)
                .forEach(row -> {
                    int current = array[row][col];
                    array[row][col] = array[array.length - row - 1][col];
                    array[array.length - row - 1][col] = current;
                }));
    }
}


