package com.baeldung.algorithms.mergeintervals;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

class MergeIntervalsUnitTest {

    private ArrayList<Interval> intervals = new ArrayList<>(Arrays.asList(
      new Interval(2, 5),
      new Interval(13, 20),
      new Interval(11, 15),
      new Interval(1, 3)
    ));
    private ArrayList<Interval> intervalsMerged = new ArrayList<>(Arrays.asList(
      new Interval(1, 5),
      new Interval(11, 20)
    ));

    @Test
    void givenIntervals_whenMerging_thenReturnMergedIntervals() {
        MergeOverlappingIntervals merger = new MergeOverlappingIntervals();
        ArrayList<Interval> result = (ArrayList<Interval>) merger.doMerge(intervals);
        assertArrayEquals(intervalsMerged.toArray(), result.toArray());
    }

}
