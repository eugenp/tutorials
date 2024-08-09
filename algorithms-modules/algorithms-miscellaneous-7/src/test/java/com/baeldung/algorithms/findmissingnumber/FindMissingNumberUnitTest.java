package com.baeldung.algorithms.findmissingnumber;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FindMissingNumberUnitTest {

    private int[] numbers;
    int N;

    @BeforeEach
    void setup() {
        numbers = new int[] { 1, 4, 5, 2, 7, 8, 6, 9 };
        N = numbers.length + 1;
    }

    @Test
    void givenIntegersArray_whenUseArithmeticSumToFindMissingNumber_thenGetMissingNumber() {
        int expectedSum = (N * (N + 1)) / 2;
        int actualSum = Arrays.stream(numbers).sum();

        int missingNumber = expectedSum - actualSum;
        assertEquals(3, missingNumber);
    }

    @Test
    void givenIntegersArray_whenUseXorToFindMissingNumber_thenGetMissingNumber() {
        int xorValue = IntStream.rangeClosed(1, N).reduce(0, (a, b) -> a ^ b);

        xorValue = Arrays.stream(numbers).reduce(xorValue, (x, y) -> x ^ y);

        assertEquals(3, xorValue);
    }

    @Test
    void givenIntegersArray_whenUseSortingToFindMissingNumber_thenGetMissingNumber() {
        Arrays.sort(numbers);

        int missingNumber = -1;
        for (int index = 0; index < numbers.length; index++) {
            if (numbers[index] != index + 1) {
                missingNumber = index + 1;
                break;
            }
        }

        assertEquals(3, missingNumber);
    }

    @Test
    void givenIntegersArray_whenTrackUsingBooleanArrayToFindMissingNumber_thenGetMissingNumber() {
        boolean[] present = new boolean[N];

        int missingNumber = -1;
        Arrays.stream(numbers).forEach(number -> present[number - 1] = true);

        for (int index = 0; index < present.length; index++) {
            if (!present[index]) {
                missingNumber = index + 1;
                break;
            }
        }

        assertEquals(3, missingNumber);
    }

    @Test
    void givenIntegersArray_whenUseBitSetToFindMissingNumber_thenGetMissingNumber() {
        BitSet bitSet = new BitSet(N);
        for (int num : numbers) {
            bitSet.set(num);
        }

        int missingNumber = bitSet.nextClearBit(1);

        assertEquals(3, missingNumber);
    }

    @Test
    void givenIntegersArrayWithMultipleMissingNumbers_whenUseBitSetToFindMultipleMissingNumbers_thenGetMultipleMissingNumbers() {
        int[] numbersWithMultipleMissing = new int[] { 1, 5, 2, 8, 9 };
        int N = Arrays.stream(numbersWithMultipleMissing)
            .max()
            .getAsInt();

        BitSet allBitSet = new BitSet(N + 1);
        IntStream.rangeClosed(1, N)
            .forEach(allBitSet::set);

        BitSet presentBitSet = new BitSet(N + 1);
        Arrays.stream(numbersWithMultipleMissing)
            .forEach(presentBitSet::set);

        allBitSet.and(presentBitSet);

        List<Integer> result = IntStream.rangeClosed(1, N)
            .filter(i -> !allBitSet.get(i))
            .boxed()
            .sorted()
            .collect(Collectors.toList());

        assertEquals(result, Arrays.asList(3, 4, 6, 7));
    }
}
