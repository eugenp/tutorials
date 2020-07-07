package com.baeldung.algorithms.quicksort;

import static com.baeldung.algorithms.quicksort.SortingUtils.compare;
import static com.baeldung.algorithms.quicksort.SortingUtils.swap;

public class DutchNationalFlagPartioning {

    public static Partition partition(int[] a, int begin, int end) {
        int lt = begin, current = begin, gt = end;
        int partitioningValue = a[begin];

        while (current <= gt) {
            int compareCurrent = compare(a[current], partitioningValue);
            switch (compareCurrent) {
                case -1:
                    swap(a, current++, lt++);
                    break;
                case 0:
                    current++;
                    break;
                case 1:
                    swap(a, current, gt--);
                    break;
            }
        }
        return new Partition(lt, gt);
    }

    public static void quicksort(int[] input, int begin, int end) {
        if (end <= begin)
            return;

        Partition middlePartition = partition(input, begin, end);

        quicksort(input, begin, middlePartition.getLeft() - 1);
        quicksort(input, middlePartition.getRight() + 1, end);
    }
}
