package com.baeldung.algorithms.rotatearray;

import static com.baeldung.algorithms.rotatearray.RotateArray.bruteForce;
import static com.baeldung.algorithms.rotatearray.RotateArray.cyclicReplacement;
import static com.baeldung.algorithms.rotatearray.RotateArray.reverse;
import static com.baeldung.algorithms.rotatearray.RotateArray.withExtraArray;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class RotateArrayUnitTest {

    private final int[] arr = { 1, 2, 3, 4, 5, 6 };
    private final int rotationLtArrayLength = 1;
    private final int rotationGtArrayLength = arr.length + 2;
    private final int[] ltArrayLengthRotation = { 6, 1, 2, 3, 4, 5 };
    private final int[] gtArrayLengthRotation = { 5, 6, 1, 2, 3, 4 };

    @Test
    void givenInputArray_whenNoRotationOrEmptyArray_thenThrowIllegalArgumentException() {
        final int noRotation = 0;
        final int someRotation = arr.length - 1;

        assertThrows(IllegalArgumentException.class, () -> bruteForce(arr, noRotation));
        assertThrows(IllegalArgumentException.class, () -> withExtraArray(arr, noRotation));
        assertThrows(IllegalArgumentException.class, () -> cyclicReplacement(arr, noRotation));
        assertThrows(IllegalArgumentException.class, () -> reverse(arr, noRotation));

        assertThrows(IllegalArgumentException.class, () -> bruteForce(null, someRotation));
        assertThrows(IllegalArgumentException.class, () -> withExtraArray(null, someRotation));
        assertThrows(IllegalArgumentException.class, () -> cyclicReplacement(null, someRotation));
        assertThrows(IllegalArgumentException.class, () -> reverse(null, someRotation));
    }

    @Test
    void givenInputArray_whenUseBruteForceRotationLtArrayLength_thenRotateArrayOk() {

        bruteForce(arr, rotationLtArrayLength);
        assertArrayEquals(ltArrayLengthRotation, arr);
    }

    @Test
    void givenInputArray_whenUseBruteForceRotationGtArrayLength_thenRotateArrayOk() {

        bruteForce(arr, rotationGtArrayLength);
        assertArrayEquals(gtArrayLengthRotation, arr);
    }

    @Test
    void givenInputArray_whenUseBruteForceRotationEqArrayLength_thenDoNothing() {
        int[] expected = arr.clone();

        bruteForce(arr, arr.length);
        assertArrayEquals(expected, arr);
    }

    @Test
    void givenInputArray_whenUseExtraArrayRotationLtArrayLength_thenRotateArrayOk() {

        withExtraArray(arr, rotationLtArrayLength);
        assertArrayEquals(ltArrayLengthRotation, arr);
    }

    @Test
    void givenInputArray_whenUseExtraArrayRotationGtArrayLength_thenRotateArrayOk() {

        withExtraArray(arr, rotationGtArrayLength);
        assertArrayEquals(gtArrayLengthRotation, arr);
    }

    @Test
    void givenInputArray_whenUseExtraArrayWithRotationEqArrayLength_thenDoNothing() {
        int[] clone = arr.clone();

        withExtraArray(arr, arr.length);
        assertArrayEquals(clone, arr);
    }

    @Test
    void givenInputArray_whenUseCyclicReplacementRotationLtArrayLength_thenRotateArrayOk() {

        cyclicReplacement(arr, rotationLtArrayLength);
        assertArrayEquals(ltArrayLengthRotation, arr);
    }

    @Test
    void givenInputArray_whenUseCyclicReplacementRotationGtArrayLength_thenRotateArrayOk() {

        cyclicReplacement(arr, rotationGtArrayLength);
        assertArrayEquals(gtArrayLengthRotation, arr);
    }

    @Test
    void givenInputArray_whenUseCyclicReplacementRotationEqArrayLength_thenDoNothing() {
        int[] clone = arr.clone();

        cyclicReplacement(arr, arr.length);
        assertArrayEquals(clone, arr);
    }

    @Test
    void givenInputArray_whenUseReverseRotationLtArrayLength_thenRotateArrayOk() {

        reverse(arr, rotationLtArrayLength);
        assertArrayEquals(ltArrayLengthRotation, arr);
    }

    @Test
    void givenInputArray_whenUseReverseRotationGtArrayLength_thenRotateArrayOk() {

        reverse(arr, rotationGtArrayLength);
        assertArrayEquals(gtArrayLengthRotation, arr);
    }

    @Test
    void givenInputArray_whenUseReverseRotationEqArrayLength_thenDoNothing() {

        int[] clone = arr.clone();

        reverse(arr, arr.length);
        assertArrayEquals(clone, arr);
    }
}
