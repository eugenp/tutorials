package com.baeldung.array.indexofmax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

public class IndexOfTheLargestElementUnitTest {

    private static final int[] ARRAY = { 1, 2, 7, 10, 8, 6 };

    public int indexOfTheMax(int[] array) {
        List<Integer> list = Arrays.stream(array)
            .boxed()
            .toList();
        int max = list.stream()
            .max(Integer::compareTo)
            .orElse(-1);
        return list.indexOf(max);
    }

    @Test
    void whenFindIndexOfTheMax_thenCorrect() {
        int result = indexOfTheMax(ARRAY);
        assertEquals(3, result);

        result = indexOfTheMax(new int[] {});
        assertEquals(-1, result);
    }

    public int indexOfTheMaxByLoop(int[] array) {
        if (array.length == 0) {
            return -1;
        }
        int idx = 0;
        for (int i = 1; i < array.length; i++) {
            idx = array[i] > array[idx] ? i : idx;
        }
        return idx;
    }

    @Test
    void whenUsingIndexOfTheMaxByLoop_thenCorrect() {
        int result = indexOfTheMaxByLoop(ARRAY);
        assertEquals(3, result);

        result = indexOfTheMaxByLoop(new int[] {});
        assertEquals(-1, result);
    }

    public int indexOfTheMaxByStream(int[] array) {
        return IntStream.range(0, array.length)
            .boxed()
            .max(Comparator.comparingInt(i -> array[i]))
            .orElse(-1);
    }

    @Test
    void whenUsingIndexOfTheMaxByStream_thenCorrect() {
        int result = indexOfTheMaxByStream(ARRAY);
        assertEquals(3, result);

        result = indexOfTheMaxByStream(new int[] {});
        assertEquals(-1, result);
    }

}