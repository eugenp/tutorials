package com.baeldung.array.smallestindex;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SmallestElementIndexUnitTest {

    @ParameterizedTest
    @MethodSource("primitiveProvider")
    void givenArray_whenUsingForLoop_thenGetCorrectResult(int[] array, int expectedIndex) {
        int minValue = Integer.MAX_VALUE;
        int minIndex = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] < minValue) {
                minValue = array[i];
                minIndex = i;
            }
        }
        assertThat(minIndex).isEqualTo(expectedIndex);
    }

    @ParameterizedTest
    @MethodSource("primitiveProvider")
    void givenArray_whenUsingForLoopAndLookForIndex_thenGetCorrectResult(int[] array, int expectedIndex) {
        int minValue = Integer.MAX_VALUE;
        for (int number : array) {
            if (number < minValue) {
                minValue = number;
            }
        }
        int minIndex = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == minValue) {
                minIndex = i;
                break;
            }
        }
        assertThat(minIndex).isEqualTo(expectedIndex);
    }

    @ParameterizedTest
    @MethodSource("primitiveProvider")
    void givenArray_whenUsingIntStreamAndLookForIndex_thenGetCorrectResult(int[] array, int expectedIndex) {
        int minValue = Arrays.stream(array).min().orElse(Integer.MAX_VALUE);
        int minIndex = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == minValue) {
                minIndex = i;
                break;
            }
        }
        assertThat(minIndex).isEqualTo(expectedIndex);
    }

    @ParameterizedTest
    @MethodSource("primitiveProvider")
    void givenArray_whenUsingIntStreamAndLookForIndexWithIntStream_thenGetCorrectResult(int[] array, int expectedIndex) {
        int minValue = Arrays.stream(array).min().orElse(Integer.MAX_VALUE);
        int minIndex = IntStream.range(0, array.length)
          .filter(index -> array[index] == minValue)
          .findFirst().orElse(-1);
        assertThat(minIndex).isEqualTo(expectedIndex);
    }

    @ParameterizedTest
    @MethodSource("primitiveProvider")
    void givenArray_whenUsingIntStreamAndLookForIndexWithArrayUtils_thenGetCorrectResult(int[] array, int expectedIndex) {
        int minValue = Arrays.stream(array).min().orElse(Integer.MAX_VALUE);
        int minIndex = ArrayUtils.indexOf(array, minValue);
        assertThat(minIndex).isEqualTo(expectedIndex);
    }

    @ParameterizedTest
    @MethodSource("referenceTypesProvider")
    void givenArray_whenUsingReduce_thenGetCorrectResult(Integer[] array, int expectedIndex) {
        int minValue = Arrays.stream(array).reduce(Integer.MAX_VALUE, Integer::min);
        int minIndex = ArrayUtils.indexOf(array, minValue);
        assertThat(minIndex).isEqualTo(expectedIndex);
    }

    @ParameterizedTest
    @MethodSource("referenceTypesProvider")
    void givenArray_whenUsingReduceAndList_thenGetCorrectResult(Integer[] array, int expectedIndex) {
        List<Integer> list = Arrays.asList(array);
        int minValue = list.stream().reduce(Integer.MAX_VALUE, Integer::min);
        int index = list.indexOf(minValue);
        assertThat(index).isEqualTo(expectedIndex);
    }

    @ParameterizedTest
    @MethodSource("primitiveProvider")
    void givenArray_whenUsingReduceWithRange_thenGetCorrectResult(int[] array, int expectedIndex) {
        int index = IntStream.range(0, array.length)
          .reduce((a, b) -> array[a] <= array[b] ? a : b)
          .orElse(-1);
        assertThat(index).isEqualTo(expectedIndex);
    }

    @ParameterizedTest
    @MethodSource("primitiveProvider")
    void givenArray_whenUsingPrimitiveStreams_thenGetCorrectResult(int[] array, int expectedIndex) {
        List<Integer> list = Arrays.stream(array).boxed().collect(Collectors.toList());
        int minValue = Arrays.stream(array).min().orElse(Integer.MAX_VALUE);
        int index = list.indexOf(minValue);
        assertThat(index).isEqualTo(expectedIndex);
    }

    static Stream<Arguments> primitiveProvider() {
        return Stream.of(
          Arguments.of(new int[]{585, 190, 201, 82, 332}, 3),
          Arguments.of(new int[]{1, 1, 1}, 0),
          Arguments.of(new int[]{}, -1)
        );
    }

    static Stream<Arguments> referenceTypesProvider() {
        return Stream.of(
          Arguments.of(new Integer[]{585, 190, 201, 82, 332}, 3),
          Arguments.of(new Integer[]{1, 1, 1}, 0),
          Arguments.of(new Integer[]{}, -1)
        );
    }
}
