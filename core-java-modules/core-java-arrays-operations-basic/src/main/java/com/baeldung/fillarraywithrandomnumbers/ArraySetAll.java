package com.baeldung.fillarraywithrandomnumbers;

import java.security.SecureRandom;
import java.util.Arrays;

public class ArraySetAll {

    public static void main(String[] args) {
        int UPPER_BOUND = 100, ARRAY_SIZE = 10;

        int[] arr = new int[ARRAY_SIZE];

        // fill content
        Arrays.setAll(arr, r -> new SecureRandom().nextInt(UPPER_BOUND));

        System.out.println(Arrays.toString(arr));
    }
}
