package com.baeldung.intrange;

import org.apache.commons.lang3.Range;

public class IntRangeApacheCommons {

    public static boolean isInClosedRange(Integer number, Integer lowerBound, Integer upperBound) {
        final Range<Integer> range = Range.between(lowerBound, upperBound);
        return range.contains(number);
    }

    public static boolean isInOpenRange(Integer number, Integer lowerBound, Integer upperBound) {
        final Range<Integer> range = Range.between(lowerBound + 1, upperBound - 1);
        return range.contains(number);
    }

    public static boolean isInOpenClosedRange(Integer number, Integer lowerBound, Integer upperBound) {
        final Range<Integer> range = Range.between(lowerBound + 1, upperBound);
        return range.contains(number);
    }

    public static boolean isInClosedOpenRange(Integer number, Integer lowerBound, Integer upperBound) {
        final Range<Integer> range = Range.between(lowerBound, upperBound - 1);
        return range.contains(number);
    }
}
