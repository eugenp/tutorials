package com.baeldung.fillarraywithrandomnumbers;

import java.util.Arrays;
import java.util.Random;

public class Iterative {

    public static void main(String[] args) {
        int UPPER_BOUND = 100, ARRAY_SIZE = 10;
        int[] arr = new int[ARRAY_SIZE];
        // random number generator
        Random random = new Random();
        // iterate and fill
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(UPPER_BOUND);
        }

        System.out.println(Arrays.toString(arr));
    }
}
