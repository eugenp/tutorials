package com.baeldung.clampfunction;

public class Clamp {

    public int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    public double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    public static <T extends Comparable<T>> T clamp(T value, T min, T max) {
        if (value.compareTo(min) < 0) {
            return min;
        } else if (value.compareTo(max) > 0) {
            return max;
        } else {
            return value;
        }
    }

}
