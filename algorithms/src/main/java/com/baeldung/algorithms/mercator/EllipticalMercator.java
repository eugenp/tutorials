package com.baeldung.algorithms.mercator;

class EllipticalMercator extends Mercator {
    @Override
    double yAxisProjection(double input) {
        if (input > 89.5) {
            input = 89.5;
        }
        if (input < -89.5) {
            input = -89.5;
        }
        double temp = RADIUS_MINOR / RADIUS_MAJOR;
        double es = 1.0 - (temp * temp);
        double eccent = Math.sqrt(es);
        double phi = Math.toRadians(input);
        double sinphi = Math.sin(phi);
        double con = eccent * sinphi;
        double com = 0.5 * eccent;
        con = Math.pow(((1.0 - con)/(1.0+con)), com);
        double ts = Math.tan(0.5 * ((Math.PI*0.5) - phi))/con;
        double y = 0 - RADIUS_MAJOR * Math.log(ts);
        return y;
    }

    @Override
    double xAxisProjection(double input) {
        return RADIUS_MAJOR * Math.toRadians(input);
    }
}
