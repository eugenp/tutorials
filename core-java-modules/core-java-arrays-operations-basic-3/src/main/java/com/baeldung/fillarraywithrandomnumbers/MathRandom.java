package com.baeldung.fillarraywithrandomnumbers;

import java.util.Arrays;

public class MathRandom {

    public static void main(String[] args) {
        int LOWER_BOUND = 1;
        int UPPER_BOUND = 100;
        int ARRAY_SIZE = 10;
        int arr[] = new int[ARRAY_SIZE];
        // iterate and fill
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (UPPER_BOUND - LOWER_BOUND)) + LOWER_BOUND;
        }

        System.out.println(Arrays.toString(arr));
    }
}
