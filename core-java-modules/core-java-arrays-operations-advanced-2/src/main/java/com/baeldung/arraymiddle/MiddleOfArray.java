package com.baeldung.arraymiddle;

import java.util.Arrays;

import org.apache.commons.lang3.ObjectUtils;

public class MiddleOfArray {
    int[] middleOfArray(int[] array) {
        if (ObjectUtils.isEmpty(array) || array.length < 3)
            return array;
        int n = array.length;
        int mid = n / 2;
        if (n % 2 == 0) {
            int mid2 = mid - 1;
            return new int[] { array[mid2], array[mid] };
        } else {
            return new int[] { array[mid] };
        }
    }

    int[] middleOfArrayWithStartEndNaive(int[] array, int start, int end) {
        int mid = (start + end) / 2;
        int n = end - start;
        if (n % 2 == 0) {
            int mid2 = mid - 1;
            return new int[] { array[mid2], array[mid] };
        } else {
            return new int[] { array[mid] };
        }
    }

    int[] middleOfArrayWithStartEnd(int[] array, int start, int end) {
        int mid = start + (end - start) / 2;
        int n = end - start;
        if (n % 2 == 0) {
            int mid2 = mid - 1;
            return new int[] { array[mid2], array[mid] };
        } else {
            return new int[] { array[mid] };
        }
    }

    int[] middleOfArrayWithStartEndBitwise(int[] array, int start, int end) {
        int mid = (start + end) >>> 1;
        int n = end - start;
        if (n % 2 == 0) {
            int mid2 = mid - 1;
            return new int[] { array[mid2], array[mid] };
        } else {
            return new int[] { array[mid] };
        }
    }

    int medianOfArray(int[] array, int start, int end) {
        Arrays.sort(array); // for safety. This can be ignored
        int mid = (start + end) >>> 1;
        int n = end - start;
        if (n % 2 == 0) {
            int mid2 = mid - 1;
            return (array[mid2] + array[mid]) / 2;
        } else {
            return array[mid];
        }
    }
}