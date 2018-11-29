package com.baeldung.hexagonal.architecture.adapters;

import com.baeldung.hexagonal.architecture.ports.SolidCalculator;

public class SolidCalculatorImpl implements SolidCalculator {

    public Double calculateVolumeOfSphere(Double radius) {
        return (Math.PI * Math.pow(radius, 3) * 4) / 3;
    }

    public Double calculateVolumeOfCube(Double sideLength) {
        return Math.pow(sideLength, 3);
    }
}
