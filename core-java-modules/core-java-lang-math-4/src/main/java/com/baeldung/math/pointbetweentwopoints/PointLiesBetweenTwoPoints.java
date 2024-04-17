package com.baeldung.math.pointbetweentwopoints;

public class PointLiesBetweenTwoPoints {
    public static boolean findUsingDistanceFormula(double x1, double y1, double x2, double y2, double x, double y) {
        double distanceAC = Math.sqrt(Math.pow(x - x1, 2) + Math.pow(y - y1, 2));
        double distanceCB = Math.sqrt(Math.pow(x2 - x, 2) + Math.pow(y2 - y, 2));
        double distanceAB = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        return Math.abs(distanceAC + distanceCB - distanceAB) < 1e-9;
    }

    public static boolean findUsingSlopeFormula(double x1, double y1, double x2, double y2, double x, double y) {
        double slopeAB = (y2 - y1) / (x2 - x1);
        double slopeAC = (y - y1) / (x - x1);
        return slopeAB == slopeAC && ((x1 <= x && x <= x2) || (x2 <= x && x <= x1)) && ((y1 <= y && y <= y2) || (y2 <= y && y <= y1));
    }
}
