package com.baeldung.algorithms.uniquedigit;

import java.util.HashSet;
import java.util.Set;

public class UniqueDigitCounter {

    public static int countWithSet(int number) {
        number = Math.abs(number);
        Set<Character> uniqueDigits = new HashSet<>();
        String numberStr = String.valueOf(number);
        for (char digit : numberStr.toCharArray()) {
            uniqueDigits.add(digit);
        }
        return uniqueDigits.size();
    }

    public static int countWithBitManipulation(int number) {
        if (number == 0) {
            return 1;
        }
        number = Math.abs(number);
        int mask = 0;
        while (number > 0) {
            int digit = number % 10;
            mask |= 1 << digit;
            number /= 10;
        }
        return Integer.bitCount(mask);
    }

    public static long countWithStreamApi(int number) {
        return String.valueOf(Math.abs(number)).chars().distinct().count();
    }

}
