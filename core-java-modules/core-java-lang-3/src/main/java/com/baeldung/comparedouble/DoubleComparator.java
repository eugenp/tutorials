package com.baeldung.comparedouble;

import java.util.Comparator;

public class DoubleComparator implements Comparator<Double> {
    private double epsilon;

    public DoubleComparator(double epsilon) {
        this.epsilon = epsilon;
    }

    @Override
    public int compare(Double d1, Double d2) {
        if (Math.abs(d1 - d2) < epsilon) {
            return 0; // equal
        } else if (d1 < d2) {
            return -1; // d1 is less than d2
        } else {
            return 1; // d1 is greater than d2
        }
    }
}