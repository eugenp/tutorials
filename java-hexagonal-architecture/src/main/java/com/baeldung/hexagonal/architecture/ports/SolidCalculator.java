package com.baeldung.hexagonal.architecture.ports;

public interface SolidCalculator {

    Double calculateVolumeOfSphere(Double radius);

    Double calculateVolumeOfCube(Double sideLength);
}
