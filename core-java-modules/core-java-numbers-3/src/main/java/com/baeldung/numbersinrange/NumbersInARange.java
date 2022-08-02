package com.baeldung.numbersinrange;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NumbersInARange {

    public List<Integer> getNumbersInRange(int start, int end) {
        List<Integer> result = new ArrayList<>();
        for (int i = start; i < end; i++) {
            result.add(i);
        }
        return result;
    }

    public List<Integer> getNumbersUsingIntStreamRange(int start, int end) {
        return IntStream.range(start, end)
          .boxed()
          .collect(Collectors.toList());
    }

    public List<Integer> getNumbersUsingIntStreamRangeClosed(int start, int end) {
        return IntStream.rangeClosed(start, end)
          .boxed()
          .collect(Collectors.toList());
    }

    public List<Integer> getNumbersUsingIntStreamIterate(int start, int limit) {
        return IntStream.iterate(start, i -> i + 1)
          .limit(limit)
          .boxed()
          .collect(Collectors.toList());
    }
}
