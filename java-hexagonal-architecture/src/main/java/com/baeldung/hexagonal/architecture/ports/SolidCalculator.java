package com.baeldung.hexagonal.architecture.ports;

import com.baeldung.hexagonal.architecture.core.Cube;
import com.baeldung.hexagonal.architecture.core.Sphere;

public interface SolidCalculator {

    Double calculateVolumeOfSphere(Sphere sphere);

    Double calculateVolumeOfCube(Cube cube);
}
