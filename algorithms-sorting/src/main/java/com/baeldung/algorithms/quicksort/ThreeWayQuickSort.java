package com.baeldung.algorithms.quicksort;

public class ThreeWayQuickSort {

    public static void threeWayQuickSort(int[] a, int begin, int end)
    {
        if (end <= begin) return;

        // partition
        int i = begin;
        int less = begin;
        int greater = end;

        while (i <= greater){
            if (a[i] < a[less]) {
                int tmp = a[i];
                a[i] = a[less];
                a[less] = tmp;

                i++;
                less++;
            }
            else if (a[less] < a[i])	{
                int tmp = a[i];
                a[i] = a[greater];
                a[greater] = tmp;

                greater--;
            }
            else {
                i++;
            }
        }

        threeWayQuickSort(a, begin, less - 1);
        threeWayQuickSort(a, greater + 1, end);
    }
}