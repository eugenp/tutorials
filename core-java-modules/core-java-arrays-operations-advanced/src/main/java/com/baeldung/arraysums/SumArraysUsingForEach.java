package com.baeldung.arraysums;

public class SumArraysUsingForEach {

    public static int[] sumOfTwoArrays(int[] arr1, int[] arr2) {
        int[] arr3 = new int[arr1.length];
        int counter = 0;
        for (int num1 : arr1) {
            arr3[counter] = num1 + arr2[counter];
            counter++;
        }
        return arr3;
    }
}
