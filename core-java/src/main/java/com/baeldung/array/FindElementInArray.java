package com.baeldung.array;

import java.util.Arrays;

public class FindElementInArray {

    public static boolean findGivenElementInArrayWithoutUsingStream(int[] array, int element) {
        boolean actualResult = false;

        for (int index = 0; index < array.length; index++) {
            if (element == array[index]) {
                actualResult = true;
                break;
            }
        }
        return actualResult;
    }

    public static boolean findGivenElementInArrayUsingStream(int[] array, int element) {
        return Arrays.stream(array).filter(x -> element == x).findFirst().isPresent();
    }
}
