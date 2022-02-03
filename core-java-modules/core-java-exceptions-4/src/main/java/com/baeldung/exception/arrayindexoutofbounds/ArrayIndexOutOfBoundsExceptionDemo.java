package com.baeldung.exception.arrayindexoutofbounds;

import java.util.Arrays;
import java.util.List;

public class ArrayIndexOutOfBoundsExceptionDemo {

    public static void main(String[] args) {
        int[] numbers = new int[] { 1, 2, 3, 4, 5 };
        
        getArrayElementAtIndex(numbers, 5);
        getListElementAtIndex(5);
        addArrayElementsUsingLoop(numbers);
    }

    public static void addArrayElementsUsingLoop(int[] numbers) {
        int sum = 0;
        for (int i = 0; i <= numbers.length; i++) {
            sum += numbers[i];
        }
    }

    public static int getListElementAtIndex(int index) {
        List<Integer> numbersList = Arrays.asList(1, 2, 3, 4, 5);
        return numbersList.get(index);
    }

    public static int getArrayElementAtIndex(int[] numbers, int index) {
        return numbers[index];
    }
}
