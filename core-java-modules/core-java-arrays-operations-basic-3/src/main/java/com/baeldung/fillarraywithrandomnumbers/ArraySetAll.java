package com.baeldung.fillarraywithrandomnumbers;

import java.security.SecureRandom;
import java.util.Arrays;

public class ArraySetAll {

    public static void main(String[] args) {
        int LOWER_BOUND = 1;
        int UPPER_BOUND = 100;
        int ARRAY_SIZE = 10;

        int arr[] = new int[ARRAY_SIZE];

        // fill content
        Arrays.setAll(arr, r -> new SecureRandom().nextInt(LOWER_BOUND, UPPER_BOUND));

        System.out.println(Arrays.toString(arr));
    }
}
