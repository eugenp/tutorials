package com.baeldung.functional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FirstClassFunctions {

    public static List<Integer> sortWithoutLambda(List<Integer> numbers) {
        Collections.sort(numbers, new Comparator<Integer>() {
            @Override
            public int compare(Integer n1, Integer n2) {
                return n1.compareTo(n2);
            }
        });
        return numbers;
    }

    public static List<Integer> sortWithLambda(List<Integer> numbers) {
        Collections.sort(numbers, (n1, n2) -> n1.compareTo(n2));
        return numbers;
    }

}
