package com.baeldung.armstrong;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ArmstrongNumberUtil {

    public static boolean isArmstrong(int n) {
        if (n < 0) {
            return false;
        }
        List<Integer> digitsList = digitsInList(n);
        int len = digitsList.size();
        int sum = digitsList.stream()
            .mapToInt(d -> (int) Math.pow(d, len))
            .sum();
        // alternatively, we can use the reduce() method:
        // int sum = digits.stream()
        //     .reduce(0, (subtotal, digit) -> subtotal + (int) Math.pow(digit, len));
        return n == sum;
    }

    private static List<Integer> digitsInList(int n) {
        List<Integer> list = new ArrayList<>();
        while (n > 0) {
            list.add(n % 10);
            n = n / 10;
        }
        return list;
    }

    public static List<Integer> getA005188Sequence(int limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("The limit cannot be a negative number.");
        }
        return IntStream.range(0, limit)
            .boxed()
            .filter(ArmstrongNumberUtil::isArmstrong)
            .collect(Collectors.toList());

    }
}
