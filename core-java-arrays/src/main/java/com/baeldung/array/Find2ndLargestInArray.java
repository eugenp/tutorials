package com.baeldung.array;

public class Find2ndLargestInArray {

    public static int find2ndLargestElement(int[] array) {
        int maxElement = array[0];
        int secondLargestElement = -1;

        for (int index = 0; index < array.length; index++) {
            if (maxElement <= array[index]) {
                secondLargestElement = maxElement;
                maxElement = array[index];
            } else if (secondLargestElement < array[index]) {
                secondLargestElement = array[index];
            }
        }
        return secondLargestElement;
    }

}
