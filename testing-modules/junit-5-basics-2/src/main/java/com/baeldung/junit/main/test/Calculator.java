package com.baeldung.junit.main.test;

import java.util.Arrays;

public class Calculator {

    public int calculateSum(String input) {
        String[] array = input.split(" ");
        int sum = Arrays.stream(array)
            .map(Integer::valueOf)
            .mapToInt(Integer::intValue)
            .sum();
        System.out.println("Calculated sum: " + sum);
        return sum;
    }

}