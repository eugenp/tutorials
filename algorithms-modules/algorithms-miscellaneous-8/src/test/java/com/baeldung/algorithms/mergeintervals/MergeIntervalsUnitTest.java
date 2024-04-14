package com.baeldung.algorithms.mergeintervals;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class MergeIntervalsUnitTest {

    private List<Interval> intervals = new ArrayList<>(Arrays.asList(
        // @formatter:off
        new Interval(3, 5),
        new Interval(13, 20),
        new Interval(11, 15),
        new Interval(15, 16),
        new Interval(1, 3),
        new Interval(4, 5),
        new Interval(16, 17)
        // @formatter:on
    ));
    private List<Interval> intervalsMerged = new ArrayList<>(Arrays.asList(
        // @formatter:off
        new Interval(1, 5),
        new Interval(11, 20)
        // @formatter:on
    ));

    @Test
    void givenIntervals_whenMerging_thenReturnMergedIntervals() {
        MergeOverlappingIntervals merger = new MergeOverlappingIntervals();
        List<Interval> result = merger.doMerge(intervals);
        assertEquals(intervalsMerged, result);
    }

}