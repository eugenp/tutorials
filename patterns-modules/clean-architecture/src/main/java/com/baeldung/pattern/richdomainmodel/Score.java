package com.baeldung.pattern.richdomainmodel;

import java.util.Arrays;

public enum Score {
    LOVE(0, "Love"), FIFTEEN(1, "Fifteen"), THIRTY(2, "Thirty"), FORTY(3, "Forty");

    private final int points;
    private final String label;

    Score(int points, String label) {
        this.points = points;
        this.label = label;
    }

    public static Score from(int value) {
        return Arrays.stream(values())
          .filter(v -> v.points == value)
          .findFirst()
          .orElseThrow(() -> new IllegalArgumentException("no such element: " + value));
    }

    public int points() {
        return points;
    }
    public String label() {
        return label;
    }
}