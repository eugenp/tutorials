package com.baeldung.algorithms.editdistance;

public class EditDistanceBase {

    public static int costOfSubstitution(char a, char b) {
        if (a == b) {
            return 0;
        }
        return 1;
    }

    public static int min(int... numbers) {
        int min = Integer.MAX_VALUE;

        for (int x : numbers) {
            if (x < min)
                min = x;
        }

        return min;
    }
}
