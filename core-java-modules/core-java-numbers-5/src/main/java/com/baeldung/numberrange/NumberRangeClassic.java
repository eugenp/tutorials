package com.baeldung.numberrange;

public class NumberRangeClassic {

    public static boolean isInClosedRange(Integer number, Integer lowerBound, Integer upperBound) {
        return (lowerBound <= number && number <= upperBound);
    }

    public static boolean isInOpenRange(Integer number, Integer lowerBound, Integer upperBound) {
        return (lowerBound < number && number < upperBound);
    }

    public static boolean isInOpenClosedRange(Integer number, Integer lowerBound, Integer upperBound) {
        return (lowerBound < number && number <= upperBound);
    }

    public static boolean isInClosedOpenRange(Integer number, Integer lowerBound, Integer upperBound) {
        return (lowerBound <= number && number < upperBound);
    }
}