package com.baeldung.exception.missingreturnstatement;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MissingReturnStatement {
    public static void main(String[] args) {
        int a = -12;
        int result = pow(a);
        System.out.println(result);
        Map<String, Integer> dictionary = createDictionary();
        dictionary.forEach((s, integer) -> System.out.println(s + " " + integer));
    }

    public static int pow(int number) {
        int pow = number * number;
        return pow;
    }

    public static String checkNumber(int number) {
        if (number == 0) {
            return "It's equals to zero";
        }

        for (int i = 0; i < number; i++) {
            if (i > 100) {
                return "It's a big number";
            }
        }
        return "It's a negative number";
    }

    public static Map<String, Integer> createDictionary() {
        List<String> words = Arrays.asList("Hello", "World");
        return words.stream()
            .collect(Collectors.toMap(s -> s, s -> 1));
    }

}
