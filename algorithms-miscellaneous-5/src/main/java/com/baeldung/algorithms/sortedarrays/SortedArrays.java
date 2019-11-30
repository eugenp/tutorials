package com.baeldung.algorithms.sortedarrays;

public class SortedArrays {

    public static int[] merge(int[] first, int[] second) {

        int firstLength = first.length;
        int secondLength = second.length;

        int[] result = new int[firstLength + secondLength];

        int firstPosition, secondPosition, resultPosition;
        firstPosition = secondPosition = resultPosition = 0;

        while (firstPosition < firstLength && secondPosition < secondLength) {

            if (first[firstPosition] < second[secondPosition]) {
                result[resultPosition++] = first[firstPosition++];
            } else {
                result[resultPosition++] = second[secondPosition++];
            }
        }

        while (firstPosition < firstLength) {
            result[resultPosition++] = first[firstPosition++];
        }

        while (secondPosition < secondLength) {
            result[resultPosition++] = second[secondPosition++];
        }

        return result;
    }
}
