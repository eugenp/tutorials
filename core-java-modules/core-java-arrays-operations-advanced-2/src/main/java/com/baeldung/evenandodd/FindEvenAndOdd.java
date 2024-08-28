package com.baeldung.evenandodd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FindEvenAndOdd {

    // Method to find Even numbers using loop
    public static List<Integer> findEvenNumbersWithLoop(int[] numbers) {
        List<Integer> evenNumbers = new ArrayList<>();
        for (int number : numbers) {
            if (number % 2 == 0) {
                evenNumbers.add(number);
            }
        }
        return evenNumbers;
    }

    // Method to find odd numbers using loop
    public static List<Integer> findOddNumbersWithLoop(int[] numbers) {
        List<Integer> oddNumbers = new ArrayList<>();
        for (int number : numbers) {
            if (number % 2 != 0) {
                oddNumbers.add(number);
            }
        }
        return oddNumbers;
    }

    // Method to find even numbers using Stream
    public static List<Integer> findEvenNumbersWithStream(int[] numbers) {
        return Arrays.stream(numbers)
                .filter(number -> number % 2 == 0)
                .boxed()
                .collect(Collectors.toList());
    }

    // Method to find odd numbers using Stream
    public static List<Integer> findOddNumbersWithStream(int[] numbers) {
        return Arrays.stream(numbers)
                .filter(number -> number % 2 != 0)
                .boxed()
                .collect(Collectors.toList());
    }
}
