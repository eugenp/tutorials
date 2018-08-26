package com.baeldung.java_8_features;

public interface Vehicle {

    void moveTo(long altitude, long longitude);

    static String producer() {
        return "N&F Vehicles";
    }

    default long[] startPosition() {
        return new long[] { 23, 15 };
    }

    default String getOverview() {
        return "ATV made by " + producer();
    }
}
