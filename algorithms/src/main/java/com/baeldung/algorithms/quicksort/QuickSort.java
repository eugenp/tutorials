package com.baeldung.algorithms.quicksort;

public class QuickSort {

    public static void main(String[] args) {
        int[] a = { 9, 5, 1, 0, 6, 2, 3, 4, 7, 8 };

        quickSort(a, 0, a.length-1);

        for (int i = 0; i < a.length; i++)
            System.out.println(a[i]);
    }

    public static void quickSort(int arr[], int begin, int end)
    {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);

            // Recursively sort elements of the 2 sub-arrays
            quickSort(arr, begin, partitionIndex-1);
            quickSort(arr, partitionIndex+1, end);
        }
    }

    private static int partition(int arr[], int begin, int end)
    {
        int pivot = arr[end];
        int i = (begin-1);

        for (int j=begin; j<end; j++)
        {
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

}
