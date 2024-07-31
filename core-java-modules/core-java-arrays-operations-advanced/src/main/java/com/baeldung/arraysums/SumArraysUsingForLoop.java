package com.baeldung.arraysums;

public class SumArraysUsingForLoop {

    public static int[] sumOfTwoArrays(int[] arr1, int[] arr2) {
        int[] arr3 = new int[arr1.length];
        for (int i = 0; i < arr1.length; i++) {
            arr3[i] = arr1[i] + arr2[i];
        }
        return arr3;
    }
}
