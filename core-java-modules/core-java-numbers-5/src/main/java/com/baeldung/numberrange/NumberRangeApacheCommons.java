package com.baeldung.numberrange;

import org.apache.commons.lang3.Range;

public class NumberRangeApacheCommons {

    public static boolean isInRange(Integer number, Integer lowerBound, Integer upperBound) {
        final Range<Integer> range = Range.between(lowerBound, upperBound);
        return range.contains(number);
    }
}
