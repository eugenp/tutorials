package com.baeldung.mergeandremoveduplicate;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class MergeArraysAndRemoveDuplicateUnitTest {

    private final Logger logger = LoggerFactory.getLogger(MergeArraysAndRemoveDuplicateUnitTest.class);

    private static String getCommaDelimited(int[] arr) {
        return Arrays.stream(arr)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining(", "));
    }

    @Test
    public void givenNoLibraryAndUnSortedArrays_whenArr1andArr2_thenMergeAndRemoveDuplicates() {
        int[] arr1 = {3, 2, 1, 4, 5, 6, 8, 7, 9};
        int[] arr2 = {8, 9, 10, 11, 12, 13, 15, 14, 15, 14, 16, 17};
        int[] expectedArr = {3, 2, 1, 4, 5, 6, 8, 7, 9, 10, 11, 12, 13, 15, 14, 16, 17};

        int[] mergedArr = MergeArraysAndRemoveDuplicate.mergeAndRemoveDuplicates(arr1, arr2);

        //merged array maintains the order of the elements in the arrays
        assertArrayEquals(expectedArr, mergedArr);

        logger.info(getCommaDelimited(mergedArr));
    }

    @Test
    public void givenNoLibraryAndSortedArrays_whenArr1andArr2_thenMergeAndRemoveDuplicates() {
        int[] arr1 = {1, 2, 3, 4, 5, 5, 6, 7, 7, 8};
        int[] arr2 = {8, 9, 10, 11, 12, 13, 14, 15, 15, 16, 17};
        int[] expectedArr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17};

        int[] mergedArr = MergeArraysAndRemoveDuplicate.mergeAndRemoveDuplicatesOnSortedArray(arr1, arr2);

        assertArrayEquals(expectedArr, mergedArr);

        logger.info(getCommaDelimited(mergedArr));
    }

    @Test
    public void givenSet_whenArr1andArr2_thenMergeAndRemoveDuplicates() {
        int[] arr1 = {3, 2, 1, 4, 5, 6, 8, 7, 9};
        int[] arr2 = {8, 9, 10, 11, 12, 13, 15, 14, 15, 14, 16, 17};
        int[] expectedArr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17};

        int[] mergedArr = MergeArraysAndRemoveDuplicate.mergeAndRemoveDuplicatesUsingSet(arr1, arr2);

        assertArrayEquals(expectedArr, mergedArr);

        logger.info(getCommaDelimited(mergedArr));
    }

    @Test
    public void givenStream_whenArr1andArr2_thenMergeAndRemoveDuplicates() {
        int[] arr1 = {3, 2, 1, 4, 5, 6, 8, 7, 9};
        int[] arr2 = {8, 9, 10, 11, 12, 13, 15, 14, 15, 14, 16, 17};
        int[] expectedArr = {3, 2, 1, 4, 5, 6, 8, 7, 9, 10, 11, 12, 13, 15, 14, 16, 17};

        int[] mergedArr = MergeArraysAndRemoveDuplicate.mergeAndRemoveDuplicatesUsingStream(arr1, arr2);

        assertArrayEquals(expectedArr, mergedArr);

        logger.info(getCommaDelimited(mergedArr));
    }
}
