package com.baeldung.array.minmax;

import org.junit.Test;

import java.util.Arrays;
import java.util.IntSummaryStatistics;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MinMax2DArrayUnitTest {

    @Test
    public void givenArray2DWhenFindMinAndMaxUsingForLoopsThenCorrect() {
        int[][] array = {{8, 4, 1}, {2, 5, 7}, {3, 6, 9}};

        int min = array[0][0];
        int max = array[0][0];
        for (int[] row : array) {
            for (int currentValue : row) {
                if (currentValue < min) {
                    min = currentValue;
                } else if (currentValue > max) {
                    max = currentValue;
                }
            }
        }

        assertEquals(1, min);
        assertEquals(9, max);
    }

    @Test
    public void givenArray2DWhenFindMinAndMaxUsingStreamThenCorrect() {
        int[][] array = {{8, 4, 1}, {2, 5, 7}, {3, 6, 9}};

        IntSummaryStatistics stats = Arrays
          .stream(array)
          .flatMapToInt(Arrays::stream)
          .summaryStatistics();

        assertEquals(1, stats.getMin());
        assertEquals(9, stats.getMax());
    }

    @Test
    public void givenArray2DWhenFindMinAndMaxUsingParallelStreamThenCorrect() {
        int[][] array = {{8, 4, 1}, {2, 5, 7}, {3, 6, 9}};

        IntSummaryStatistics stats = Arrays
          .stream(array)
          .parallel()
          .flatMapToInt(Arrays::stream)
          .summaryStatistics();

        assertEquals(1, stats.getMin());
        assertEquals(9, stats.getMax());
    }

}
