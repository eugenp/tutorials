package com.baeldung.numberrange;

import java.time.temporal.ValueRange;

public class NumberRangeValueRange {

    public static boolean isInRange(Integer number, Integer lowerBound, Integer upperBound) {
        final ValueRange range = ValueRange.of(lowerBound, upperBound);
        return range.isValidIntValue(number);
    }
}
