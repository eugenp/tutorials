package com.baeldung.javadoublevsbigdecimal;

public class DoubleExample {
    public static double performDoubleComparison(double d) {
        double expected = 0.1;
        double epsilon = 0.0000000000000001;

        if (Math.abs(expected - d) < epsilon) {
            return d;
        } else {
            return -1;
        }
    }
}
