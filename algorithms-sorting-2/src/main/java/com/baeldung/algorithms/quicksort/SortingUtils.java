package com.baeldung.algorithms.quicksort;

public class SortingUtils {

    public static void swap(int[] array, int position1, int position2) {
        if (position1 != position2) {
            int temp = array[position1];
            array[position1] = array[position2];
            array[position2] = temp;
        }
    }

    public static int compare(int num1, int num2) {
        if (num1 > num2)
            return 1;
        else if (num1 < num2)
            return -1;
        else
            return 0;
    }

    public static void printArray(int[] array) {
        if (array == null) {
            return;
        }
        for (int e : array) {
            System.out.print(e + " ");
        }
        System.out.println();
    }

}
