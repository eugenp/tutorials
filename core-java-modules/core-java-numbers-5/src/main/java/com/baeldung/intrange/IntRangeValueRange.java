package com.baeldung.intrange;

import java.time.temporal.ValueRange;

public class IntRangeValueRange {

    public static boolean isInClosedRange(Integer number, Integer lowerBound, Integer upperBound) {
        final ValueRange range = ValueRange.of(lowerBound, upperBound);
        return range.isValidIntValue(number);
    }

    public static boolean isInOpenRange(Integer number, Integer lowerBound, Integer upperBound) {
        final ValueRange range = ValueRange.of(lowerBound + 1, upperBound - 1);
        return range.isValidIntValue(number);
    }

    public static boolean isInOpenClosedRange(Integer number, Integer lowerBound, Integer upperBound) {
        final ValueRange range = ValueRange.of(lowerBound + 1, upperBound);
        return range.isValidIntValue(number);
    }

    public static boolean isInClosedOpenRange(Integer number, Integer lowerBound, Integer upperBound) {
        final ValueRange range = ValueRange.of(lowerBound, upperBound - 1);
        return range.isValidIntValue(number);
    }
}
