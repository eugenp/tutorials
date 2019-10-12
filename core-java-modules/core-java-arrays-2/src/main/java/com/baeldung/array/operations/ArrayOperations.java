package com.baeldung.array.operations;

public class ArrayOperations {

    public static Integer[] addElementUsingPureJava(Integer[] srcArray, int elementToAdd) {
        Integer[] destArray = new Integer[srcArray.length + 1];

        for (int i = 0; i < srcArray.length; i++) {
            destArray[i] = srcArray[i];
        }

        destArray[destArray.length - 1] = elementToAdd;
        return destArray;
    }

    public static int[] insertAnElementAtAGivenIndex(final int[] srcArray, int index, int newElement) {
        int[] destArray = new int[srcArray.length + 1];
        int j = 0;
        for (int i = 0; i < destArray.length - 1; i++) {

            if (i == index) {
                destArray[i] = newElement;
            } else {
                destArray[i] = srcArray[j];
                j++;
            }
        }
        return destArray;
    }
}
