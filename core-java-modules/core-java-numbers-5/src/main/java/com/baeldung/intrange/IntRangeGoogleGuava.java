package com.baeldung.intrange;

import com.google.common.collect.Range;

public class IntRangeGoogleGuava {

    public static boolean isInClosedRange(Integer number, Integer lowerBound, Integer upperBound) {
        final Range<Integer> range = Range.closed(lowerBound, upperBound);
        return range.contains(number);
    }

    public static boolean isInOpenRange(Integer number, Integer lowerBound, Integer upperBound) {
        final Range<Integer> range = Range.open(lowerBound, upperBound);
        return range.contains(number);
    }

    public static boolean isInOpenClosedRange(Integer number, Integer lowerBound, Integer upperBound) {
        final Range<Integer> range = Range.openClosed(lowerBound, upperBound);
        return range.contains(number);
    }

    public static boolean isInClosedOpenRange(Integer number, Integer lowerBound, Integer upperBound) {
        final Range<Integer> range = Range.closedOpen(lowerBound, upperBound);
        return range.contains(number);
    }
}
