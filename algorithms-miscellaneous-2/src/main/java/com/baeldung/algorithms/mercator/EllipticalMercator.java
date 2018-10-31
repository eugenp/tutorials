package com.baeldung.algorithms.mercator;

class EllipticalMercator extends Mercator {

    @Override
    double yAxisProjection(double input) {

        input = Math.min(Math.max(input, -89.5), 89.5);
        double earthDimensionalRateNormalized = 1.0 - Math.pow(RADIUS_MINOR / RADIUS_MAJOR, 2);

        double inputOnEarthProj = Math.sqrt(earthDimensionalRateNormalized) *  Math.sin( Math.toRadians(input));

        inputOnEarthProj = Math.pow(((1.0 - inputOnEarthProj)/(1.0+inputOnEarthProj)), 0.5 * Math.sqrt(earthDimensionalRateNormalized));
        double inputOnEarthProjNormalized = Math.tan(0.5 * ((Math.PI*0.5) -  Math.toRadians(input)))/inputOnEarthProj;
        return (-1) * RADIUS_MAJOR * Math.log(inputOnEarthProjNormalized);
    }

    @Override
    double xAxisProjection(double input) {
        return RADIUS_MAJOR * Math.toRadians(input);
    }
}
