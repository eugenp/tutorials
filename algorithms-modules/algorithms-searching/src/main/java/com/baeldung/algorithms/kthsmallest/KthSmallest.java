package com.baeldung.algorithms.kthsmallest;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class KthSmallest {

    public static int findKthSmallestElement(int k, int[] list1, int[] list2)  throws NoSuchElementException, IllegalArgumentException {

        checkInput(k, list1, list2);

        // we are looking for the minimum value
        if(k == 1) {
            return min(list1[0], list2[0]);
        }

        // we are looking for the maximum value
        if(list1.length + list2.length == k) {
            return max(list1[list1.length-1], list2[list2.length-1]);
        }

        // swap lists if needed to make sure we take at least one element from list1
        if(k <= list2.length && list2[k-1] < list1[0]) {
            int[] list1_ = list1;
            list1 = list2;
            list2 = list1_;
        }

        // correct left boundary if k is bigger than the size of list2
        int left = k < list2.length ? 0 : k - list2.length - 1;

        // the inital right boundary cannot exceed the list1
        int right = min(k-1, list1.length - 1);

        int nElementsList1, nElementsList2;

        // binary search
        do {
            nElementsList1 = ((left + right) / 2) + 1;
            nElementsList2 = k - nElementsList1;

            if(nElementsList2 > 0) {
                if (list1[nElementsList1 - 1] > list2[nElementsList2 - 1]) {
                    right = nElementsList1 - 2;
                } else {
                    left = nElementsList1;
                }
            }
        } while(!kthSmallesElementFound(list1, list2, nElementsList1, nElementsList2));

        return nElementsList2 == 0 ? list1[nElementsList1-1] : max(list1[nElementsList1-1], list2[nElementsList2-1]);
    }

    private static boolean kthSmallesElementFound(int[] list1, int[] list2, int nElementsList1, int nElementsList2) {

        // we do not take any element from the second list
        if(nElementsList2 < 1) {
            return true;
        }

        if(list1[nElementsList1-1] == list2[nElementsList2-1]) {
            return true;
        }

        if(nElementsList1 == list1.length) {
            return list1[nElementsList1-1] <= list2[nElementsList2];
        }

        if(nElementsList2 == list2.length) {
            return list2[nElementsList2-1] <= list1[nElementsList1];
        }

        return list1[nElementsList1-1] <= list2[nElementsList2] && list2[nElementsList2-1] <= list1[nElementsList1];
    }


    private static void checkInput(int k, int[] list1, int[] list2) throws NoSuchElementException, IllegalArgumentException {

        if(list1 == null || list2 == null || k < 1) {
            throw new IllegalArgumentException();
        }

        if(list1.length == 0 || list2.length == 0) {
            throw new IllegalArgumentException();
        }

        if(k > list1.length + list2.length) {
            throw new NoSuchElementException();
        }
    }

    public static int getKthElementSorted(int[] list1, int[] list2, int k) {

        int length1 = list1.length, length2 = list2.length;
        int[] combinedArray = new int[length1 + length2];
        System.arraycopy(list1, 0, combinedArray, 0, list1.length);
        System.arraycopy(list2, 0, combinedArray, list1.length, list2.length);
        Arrays.sort(combinedArray);

        return combinedArray[k-1];
    }

    public static int getKthElementMerge(int[] list1, int[] list2, int k) {

        int i1 = 0, i2 = 0;

        while(i1 < list1.length && i2 < list2.length && (i1 + i2) < k) {
            if(list1[i1] < list2[i2]) {
                i1++;
            } else {
                i2++;
            }
        }

        if((i1 + i2) < k) {
            return i1 < list1.length ? list1[k - i2 - 1] : list2[k - i1 - 1];
        } else if(i1 > 0 && i2 > 0) {
            return Math.max(list1[i1-1], list2[i2-1]);
        } else {
            return i1 == 0 ? list2[i2-1] : list1[i1-1];
        }
    }
}
