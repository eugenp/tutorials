package com.baeldung.streams.conversions;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

public class StreamsTo2DArrayConversionUnitTest {

    @Test
    public void given2DArray_whenUsingArrayStream_thenConvertToStream() {
        int[][] array2D = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };

        Stream<int[]> streamOfRows = Arrays.stream(array2D);
        int[][] resultArray2D = streamOfRows.toArray(int[][]::new);

        assertArrayEquals(array2D, resultArray2D);
    }

    @Test
    public void given2DArray_whenUsingArrayStream_thenConvertToFlatStream() {
        int[][] array2D = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };

        IntStream flatStream = Arrays.stream(array2D)
            .flatMapToInt(Arrays::stream);
        int[] resultFlatArray = flatStream.toArray();

        int[] expectedFlatArray = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

        assertArrayEquals(expectedFlatArray, resultFlatArray);
    }

    @Test
    public void givenStream_whenUsingArrayStream_thenConvertTo2DArray() {
        int[][] originalArray2D = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };

        Stream<int[]> streamOfRows = Arrays.stream(originalArray2D);

        int[][] resultArray2D = streamOfRows.toArray(int[][]::new);

        assertArrayEquals(originalArray2D, resultArray2D);
    }

    @Test
    public void givenFlatStream_whenUsingArrayStream_thenConvertTo2DArray() {
        int[] flatArray = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        IntStream flatStream = Arrays.stream(flatArray);
        int rows = 3;
        int cols = 3;

        int[][] expectedArray2D = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };

        int[][] resultArray2D = new int[rows][cols];
        int[] collectedArray = flatStream.toArray();
        for (int i = 0; i < rows; i++) {
            System.arraycopy(collectedArray, i * cols, resultArray2D[i], 0, cols);
        }

        assertArrayEquals(expectedArray2D, resultArray2D);
    }
}
