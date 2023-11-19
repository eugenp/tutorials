package com.baeldung.arrayindex;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

class ArrayIndex {
    static int forLoop(int[] numbers, int target) {
        for (int index = 0; index < numbers.length; index++) {
            if (numbers[index] == target) {
                return index;
            }
        }
        return -1;
    }

    static int listIndexOf(Integer[] numbers, int target) {
        List<Integer> list = Arrays.asList(numbers);
        return list.indexOf(target);
    }

    static int intStream(int[] numbers, int target) {
        return IntStream.range(0, numbers.length)
          .filter(i -> numbers[i] == target)
          .findFirst()
          .orElse(-1);
    }
}