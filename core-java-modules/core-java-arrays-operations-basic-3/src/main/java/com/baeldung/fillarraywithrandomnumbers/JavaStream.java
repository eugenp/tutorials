package com.baeldung.fillarraywithrandomnumbers;

import java.util.Arrays;
import java.util.Random;

public class JavaStream {

    public static void main(String[] args) {
        int LOWER_BOUND = 1;
        int UPPER_BOUND = 100;
        int ARRAY_SIZE = 10;
        // random number generator
        Random random = new Random();
        // fill with ints method
        int arr[] = random.ints(ARRAY_SIZE, LOWER_BOUND, UPPER_BOUND).toArray();

        System.out.println(Arrays.toString(arr));
    }
}
