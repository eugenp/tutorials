package com.baeldung.algorithms.countinginversions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CountingInversionsUnitTest {

    @Test
    void givenArray_whenCountingInversions_thenReturnCorrectCount() {
        int[] input = {3, 1, 2};
        int expectedInversions = 2;

        int actualInversions = countInversionsBruteForce(input);

        assertEquals(expectedInversions, actualInversions);
    }

    int countInversionsBruteForce(int[] array) {
        int inversionCount = 0;
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] > array[j]) {
                    inversionCount++;
                }
            }
        }
        return inversionCount;
    }

    @Test
    void givenArray_whenCountingInversionsWithOptimizedMethod_thenReturnCorrectCount() {
        int[] inputArray = {3, 1, 2};
        int expectedInversions = 2;

        int actualInversions = countInversionsDivideAndConquer(inputArray);

        assertEquals(expectedInversions, actualInversions);
    }

    int countInversionsDivideAndConquer(int[] array) {
        if (array == null || array.length <= 1) {
            return 0;
        }
        return mergeSortAndCount(array, 0, array.length - 1);
    }

    int mergeSortAndCount(int[] array, int left, int right) {
        if (left >= right) {
            return 0;
        }

        int mid = left + (right - left) / 2;
        int inversions = mergeSortAndCount(array, left, mid) + mergeSortAndCount(array, mid + 1, right);

        inversions += mergeAndCount(array, left, mid, right);
        return inversions;
    }

    int mergeAndCount(int[] array, int left, int mid, int right) {
        int[] leftArray = new int[mid - left + 1];
        int[] rightArray = new int[right - mid];

        System.arraycopy(array, left, leftArray, 0, mid - left + 1);
        System.arraycopy(array, mid + 1, rightArray, 0, right - mid);

        int i = 0, j = 0, k = left, inversions = 0;

        while (i < leftArray.length && j < rightArray.length) {
            if (leftArray[i] <= rightArray[j]) {
                array[k++] = leftArray[i++];
            } else {
                array[k++] = rightArray[j++];
                inversions += leftArray.length - i;
            }
        }

        while (i < leftArray.length) {
            array[k++] = leftArray[i++];
        }

        while (j < rightArray.length) {
            array[k++] = rightArray[j++];
        }

        return inversions;
    }

}
