package com.baeldung.comparedouble;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    public static boolean areEqual(double d1, double d2, int decimalPlaces) {
        BigDecimal bd1 = BigDecimal.valueOf(d1)
            .setScale(decimalPlaces, RoundingMode.HALF_UP);
        BigDecimal bd2 = BigDecimal.valueOf(d2)
            .setScale(decimalPlaces, RoundingMode.HALF_UP);
        return bd1.equals(bd2);
    }
}