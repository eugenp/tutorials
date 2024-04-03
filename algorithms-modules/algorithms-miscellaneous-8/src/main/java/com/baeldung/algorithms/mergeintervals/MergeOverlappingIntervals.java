package com.baeldung.algorithms.mergeintervals;

import java.util.ArrayList;
import java.util.List;

public class MergeOverlappingIntervals {

    public List<Interval> doMerge(List<Interval> intervals) {
        // Sort the intervals based on start time
        intervals.sort((one, two) -> one.start - two.start);

        // Create somewhere to put the merged list, start it off with the earliest starting interval
        ArrayList<Interval> merged = new ArrayList<>();
        merged.add(intervals.get(0));

        // Loop over each interval and merge if start time is before the end time of the
        // previous interval
        intervals.forEach(interval -> {
            if (merged.get(merged.size() - 1).end > interval.start) {
                merged.get(merged.size() - 1).setEnd(interval.end);
            } else {
                merged.add(interval);
            }
        });

        return merged;
    }

}
