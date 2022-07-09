package com.baeldung.numberrange;

public class NumberRangeFormula {

    public static boolean isInRangeUsingFormula(Integer number, Integer lowerBound, Integer upperBound) {
        return Math.abs(number - lowerBound) + Math.abs(upperBound - number) == Math.abs(upperBound - lowerBound);
    }

    public static boolean isInRangeUsingFormula2(Integer number, Integer lowerBound, Integer upperBound) {
        return (number - lowerBound) * (upperBound - number) >= 0;
    }
}
