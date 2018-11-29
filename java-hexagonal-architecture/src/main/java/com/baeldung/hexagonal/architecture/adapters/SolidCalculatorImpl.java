package com.baeldung.hexagonal.architecture.adapters;

import com.baeldung.hexagonal.architecture.core.Cube;
import com.baeldung.hexagonal.architecture.core.Sphere;
import com.baeldung.hexagonal.architecture.ports.SolidCalculator;

public class SolidCalculatorImpl implements SolidCalculator {

    public Double calculateVolumeOfSphere(Sphere sphere) {
        Double radius = sphere.getRadius();
        return (Math.PI * Math.pow(radius, 3) * 4) / 3;
    }

    public Double calculateVolumeOfCube(Cube cube) {
        Double sideLength = cube.getSideLength();
        return Math.pow(sideLength, 3);
    }
}
