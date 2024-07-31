package com.baeldung.array.flatarray;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class FlatArrayUnitTest {

    @ParameterizedTest
    @MethodSource("arrayProvider")
    void giveTwoDimensionalArray_whenFlatWithStream_thenGetCorrectResult(int[][] initialArray,
      int[] expected) {
        int[] actual = Arrays.stream(initialArray).flatMapToInt(Arrays::stream).toArray();
        assertThat(actual).containsExactly(expected);
    }

    @ParameterizedTest
    @MethodSource("arrayProvider")
    void giveTwoDimensionalArray_whenFlatWithForLoopAndAdditionalList_thenGetCorrectResult(int[][] initialArray,
      int[] intArray) {
        List<Integer> expected = Arrays.stream(intArray).boxed().collect(Collectors.toList());
        List<Integer> actual = new ArrayList<>();
        for (int[] numbers : initialArray) {
            for (int number : numbers) {
                actual.add(number);
            }
        }
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("arrayProvider")
    void giveTwoDimensionalArray_whenFlatWithForLoopAndLists_thenGetCorrectResult(int[][] initialArray,
      int[] intArray) {
        List<Integer> expected = Arrays.stream(intArray).boxed().collect(Collectors.toList());
        List<Integer> actual = new ArrayList<>();
        for (int[] numbers : initialArray) {
            List<Integer> listOfNumbers = Arrays.stream(numbers).boxed().collect(Collectors.toList());
            actual.addAll(listOfNumbers);
        }
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("arrayProvider")
    void giveTwoDimensionalArray_whenFlatWithArrayCopy_thenGetCorrectResult(int[][] initialArray,
      int[] expected) {
        int[] actual = new int[]{};
        int position = 0;
        for (int[] numbers : initialArray) {
            if (actual.length < position + numbers.length) {
                int[] newArray = new int[actual.length + numbers.length];
                System.arraycopy(actual, 0, newArray, 0, actual.length);
                actual = newArray;
            }
            System.arraycopy(numbers, 0, actual, position, numbers.length);
            position += numbers.length;
        }
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("arrayProvider")
    void giveTwoDimensionalArray_whenFlatWithArrayCopyAndTotalNumberOfElements_thenGetCorrectResult(int[][] initialArray,
      int[] expected) {
        int totalNumberOfElements = 0;
        for (int[] numbers : initialArray) {
            totalNumberOfElements += numbers.length;
        }
        int[] actual = new int[totalNumberOfElements];
        int position = 0;
        for (int[] numbers : initialArray) {
            System.arraycopy(numbers, 0, actual, position, numbers.length);
            position += numbers.length;
        }
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("arrayProvider")
    void giveTwoDimensionalArray_whenFlatWithForLoopAndTotalNumberOfElements_thenGetCorrectResult(int[][] initialArray,
      int[] expected) {
        int totalNumberOfElements = 0;
        for (int[] numbers : initialArray) {
            totalNumberOfElements += numbers.length;
        }
        int[] actual = new int[totalNumberOfElements];
        int position = 0;
        for (int[] numbers : initialArray) {
            for (int number : numbers) {
                actual[position] = number;
                ++position;
            }
        }
        assertThat(actual).isEqualTo(expected);
    }


    static Stream<Arguments> arrayProvider() {
        return Stream.of(
          Arguments.of(
            new int[][]{
              {805, 902, 259, 162, 775},
              {278, 216, 0, 72, 663},
              {185, 390, 537, 909, 918},
              {150, 782, 282, 482, 401},
              {244, 685, 643, 364, 307},
              {483, 939, 750, 190, 424},
              {44, 160, 290, 963, 881}
            },
            new int[]{
              805, 902, 259, 162, 775,
              278, 216, 0, 72, 663,
              185, 390, 537, 909, 918,
              150, 782, 282, 482, 401,
              244, 685, 643, 364, 307,
              483, 939, 750, 190, 424,
              44, 160, 290, 963, 881
            }
          )
        );
    }
}
