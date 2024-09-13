package com.baeldung.fillarraywithrandomnumbers;

import java.util.Arrays;
import java.util.Random;

public class SeedExample {

    public static void main(String[] args) {

        int LOWER_BOUND = 1;
        int UPPER_BOUND = 100;
        int ARRAY_SIZE = 10;

        // Produce identical elements repeatedly
        int arr[] = new Random(12345).ints(ARRAY_SIZE, LOWER_BOUND, UPPER_BOUND).toArray();

        int arr2[] = new Random(12345).ints(ARRAY_SIZE, LOWER_BOUND, UPPER_BOUND).toArray();

        System.out.printf("Arr: %s%n", Arrays.toString(arr));
        System.out.printf("Arr2: %s%n", Arrays.toString(arr2));

        // using different seeds
        int arr3[] = new Random(54321).ints(ARRAY_SIZE, LOWER_BOUND, UPPER_BOUND).toArray();

        System.out.printf("%nArr2: %s%n", Arrays.toString(arr2));
        System.out.printf("Arr3: %s%n", Arrays.toString(arr3));
    }
}
