package com.baeldung.algorithms.quicksort;

import static com.baeldung.algorithms.quicksort.SortingUtils.swap;

public class BentleyMcIlroyPartioning {

    public static Partition partition(int input[], int begin, int end) {
        int left = begin, right = end;
        int leftEqualKeysCount = 0, rightEqualKeysCount = 0;

        int partitioningValue = input[end];

        while (true) {
            while (input[left] < partitioningValue)
                left++;

            while (input[right] > partitioningValue) {
                if (right == begin)
                    break;
                right--;
            }

            if (left == right && input[left] == partitioningValue) {
                swap(input, begin + leftEqualKeysCount, left);
                leftEqualKeysCount++;
                left++;
            }

            if (left >= right) {
                break;
            }

            swap(input, left, right);

            if (input[left] == partitioningValue) {
                swap(input, begin + leftEqualKeysCount, left);
                leftEqualKeysCount++;
            }

            if (input[right] == partitioningValue) {
                swap(input, right, end - rightEqualKeysCount);
                rightEqualKeysCount++;
            }
            left++; right--;
        }
        right = left - 1;
        for (int k = begin; k < begin + leftEqualKeysCount; k++, right--) {
            if (right >= begin + leftEqualKeysCount)
                swap(input, k, right);
        }
        for (int k = end; k > end - rightEqualKeysCount; k--, left++) {
            if (left <= end - rightEqualKeysCount)
                swap(input, left, k);
        }
        return new Partition(right + 1, left - 1);
    }

    public static void quicksort(int input[], int begin, int end) {
        if (end <= begin)
            return;
        Partition middlePartition = partition(input, begin, end);
        quicksort(input, begin, middlePartition.getLeft() - 1);
        quicksort(input, middlePartition.getRight() + 1, end);
    }

}
