package com.baeldung.collectionsvsarrays.sorting;

import java.util.Comparator;
import java.util.List;

public class Quicksort {

    public static void sort(int arr[]) {
        sort(arr, 0, arr.length - 1);
    }
    public static void sort(int arr[], int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);

            sort(arr, begin, partitionIndex-1);
            sort(arr, partitionIndex+1, end);
        }
    }
    private static int partition(int arr[], int begin, int end) {
        int pivot = arr[end];
        int i = (begin-1);

        for (int j = begin; j < end; j++) {
            if (arr[j] <= pivot) {
                i++;

                int swapTemp = arr[i];
                arr[i] = arr[j];
                arr[j] = swapTemp;
            }
        }

        int swapTemp = arr[i+1];
        arr[i+1] = arr[end];
        arr[end] = swapTemp;

        return i+1;
    }

    public static <T> void sort(List<T> list, Comparator<T> comparator) {
        sort(list, 0, list.size() - 1, comparator);
    }
    public static <T> void sort(List<T> list, int low, int high, Comparator<T> comparator) {
        if (low < high) {
            int partitionIndex = partition(list, low, high, comparator);

            sort(list, low, partitionIndex - 1, comparator);
            sort(list, partitionIndex + 1, high, comparator);
        }
    }

    private static <T> int partition(List<T> list, int begin, int end, Comparator<T> comparator) {
        T pivot = list.get(end);
        int i = (begin-1);

        for (int j = begin; j < end; j++) {
            if (comparator.compare(list.get(j), pivot) <= 0) {
                i++;

                T swapTemp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, swapTemp);
            }
        }

        T swapTemp = list.get(i+1);
        list.set(i+1,list.get(end));
        list.set(end, swapTemp);

        return i+1;
    }
}
