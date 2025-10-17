/**
 * Package to host code for calculating three types of angle difference
 */
package com.baeldung.algorithms.twoanglesdifference;

public class AngleDifferenceCalculator {

    /**
     * Normalizes an angle to be within the range [0, 360).
     *
     * @param angle The angle in degrees.
     * @return The normalized angle.
     */
    public static double normalizeAngle(double angle) {
        return (angle % 360 + 360) % 360;
    }

    /**
     * Calculates the absolute difference between two angles.
     *
     * @param angle1 The first angle in degrees.
     * @param angle2 The second angle in degrees.
     * @return The absolute difference in degrees.
     */
    public static double absoluteDifference(double angle1, double angle2) {
        return Math.abs(angle1 - angle2);
    }

    /**
     * Calculates the shortest difference between two angles.
     *
     * @param angle1 The first angle in degrees.
     * @param angle2 The second angle in degrees.
     * @return The shortest difference in degrees (0 to 180).
     */
    public static double shortestDifference(double angle1, double angle2) {
        double diff = absoluteDifference(normalizeAngle(angle1), normalizeAngle(angle2));
        return Math.min(diff, 360 - diff);
    }

    /**
     * Calculates the signed shortest difference between two angles.
     * A positive result indicates counter-clockwise rotation, a negative result indicates clockwise.
     *
     * @param angle1 The first angle in degrees.
     * @param angle2 The second angle in degrees.
     * @return The signed shortest difference in degrees (-180 to 180).
     */
    public static double signedShortestDifference(double angle1, double angle2) {
        double normalizedAngle1 = normalizeAngle(angle1);
        double normalizedAngle2 = normalizeAngle(angle2);
        double diff = normalizedAngle2 - normalizedAngle1;

        if (diff > 180) {
            return diff - 360;
        } else if (diff < -180) {
            return diff + 360;
        } else {
            return diff;
        }
    }
}


