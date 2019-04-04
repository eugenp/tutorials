package com.baeldung.algorithms.editdistance;

import java.util.Arrays;

public class EditDistanceBase {

    static int costOfSubstitution(char a, char b) {
        return a == b ? 0 : 1;
    }

    static int min(int... numbers) {
        return Arrays.stream(numbers)
          .min().orElse(Integer.MAX_VALUE);
    }
}
