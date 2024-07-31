package com.baeldung.array.conversions;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import com.google.common.primitives.Ints;

public class PrimitiveIntArrayToHashSetUnitTest {
    int[] arr = { 1, 2, 3, 4, 5 };
    HashSet<Integer> expected = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));

    @Test
    public void givenPrimitiveIntArray_whenConvertingByDirectConstructor_thenGiveWrongResult() {
        HashSet<int[]> result = new HashSet<>(Arrays.asList(arr));
        assertEquals(1, result.size());
        assertNotEquals(expected, result);
    }

    @Test
    public void givenPrimitiveIntArray_whenConvertingByLoop_thenSuccess() {
        HashSet<Integer> result = new HashSet<>();
        for (int num : arr) {
            result.add(num);
        }
        assertEquals(expected, result);
    }

    @Test
    public void givenPrimitiveIntArray_whenConvertingByStreams_thenSuccess() {
        HashSet<Integer> result = Arrays.stream(arr).boxed().collect(Collectors.toCollection(HashSet::new));
        assertEquals(expected, result);
    }

    @Test
    public void givenPrimitiveIntArray_whenConvertingByArrayUtils_thenSuccess() {
        HashSet<Integer> result = new HashSet<>(Arrays.asList(ArrayUtils.toObject(arr)));
        assertEquals(expected, result);
    }

    @Test
    public void givenPrimitiveIntArray_whenConvertingByGuava_thenSuccess() {
        HashSet<Integer> result = new HashSet<>(Ints.asList(arr));
        assertEquals(expected, result);
    }

}
