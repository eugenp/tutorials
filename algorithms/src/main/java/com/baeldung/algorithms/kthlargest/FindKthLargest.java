package com.baeldung.algorithms.kthlargest;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.IntStream;

public class FindKthLargest {

    public int findKthLargestBySorting(Integer[] arr, int k) {
        Arrays.sort(arr);
        int targetIndex = arr.length - k;
        return arr[targetIndex];
    }

    public int findKthLargestBySortingDesc(Integer[] arr, int k) {
        Arrays.sort(arr, Collections.reverseOrder());
        return arr[k - 1];
    }

    public int findKthElementByQuickSelect(Integer[] arr, int left, int right, int k) {
        if (k >= 0 && k <= right - left + 1) {
            int pos = partition(arr, left, right);
            if (pos - left == k) {
                return arr[pos];
            }
            if (pos - left > k) {
                return findKthElementByQuickSelect(arr, left, pos - 1, k);
            }
            return findKthElementByQuickSelect(arr, pos + 1, right, k - pos + left - 1);
        }
        return 0;
    }

    public int findKthElementByQuickSelectWithIterativePartition(Integer[] arr, int left, int right, int k) {
        if (k >= 0 && k <= right - left + 1) {
            int pos = partitionIterative(arr, left, right);
            if (pos - left == k) {
                return arr[pos];
            }
            if (pos - left > k) {
                return findKthElementByQuickSelectWithIterativePartition(arr, left, pos - 1, k);
            }
            return findKthElementByQuickSelectWithIterativePartition(arr, pos + 1, right, k - pos + left - 1);
        }
        return 0;
    }

    private int partition(Integer[] arr, int left, int right) {
        int pivot = arr[right];
        Integer[] leftArr;
        Integer[] rightArr;

        leftArr = IntStream.range(left, right)
            .filter(i -> arr[i] < pivot)
            .map(i -> arr[i])
            .boxed()
            .toArray(Integer[]::new);

        rightArr = IntStream.range(left, right)
            .filter(i -> arr[i] > pivot)
            .map(i -> arr[i])
            .boxed()
            .toArray(Integer[]::new);

        int leftArraySize = leftArr.length;
        System.arraycopy(leftArr, 0, arr, left, leftArraySize);
        arr[leftArraySize + left] = pivot;
        System.arraycopy(rightArr, 0, arr, left + leftArraySize + 1, rightArr.length);

        return left + leftArraySize;
    }

    private int partitionIterative(Integer[] arr, int left, int right) {
        int pivot = arr[right], i = left;
        for (int j = left; j <= right - 1; j++) {
            if (arr[j] <= pivot) {
                swap(arr, i, j);
                i++;
            }
        }
        swap(arr, i, right);
        return i;
    }

    public int findKthElementByRandomizedQuickSelect(Integer[] arr, int left, int right, int k) {
        if (k >= 0 && k <= right - left + 1) {
            int pos = randomPartition(arr, left, right);
            if (pos - left == k) {
                return arr[pos];
            }
            if (pos - left > k) {
                return findKthElementByRandomizedQuickSelect(arr, left, pos - 1, k);
            }
            return findKthElementByRandomizedQuickSelect(arr, pos + 1, right, k - pos + left - 1);
        }
        return 0;
    }

    private int randomPartition(Integer arr[], int left, int right) {
        int n = right - left + 1;
        int pivot = (int) (Math.random() * n);
        swap(arr, left + pivot, right);
        return partition(arr, left, right);
    }

    private void swap(Integer[] arr, int n1, int n2) {
        int temp = arr[n2];
        arr[n2] = arr[n1];
        arr[n1] = temp;
    }
}
