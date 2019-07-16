package com.baeldung.algorithms.mercator;

abstract class Mercator {
    final static double RADIUS_MAJOR = 6378137.0;
    final static double RADIUS_MINOR = 6356752.3142;

    abstract double yAxisProjection(double input);

    abstract double xAxisProjection(double input);
}
