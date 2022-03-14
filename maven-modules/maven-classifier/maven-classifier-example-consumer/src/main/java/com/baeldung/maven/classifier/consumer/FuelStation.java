
package com.baeldung.maven.classifier.consumer;

import com.baeldung.maven.dependency.classifier.provider.model.Car;
import com.baeldung.maven.dependency.classifier.provider.model.PowerSource;

public class FuelStation {

    public FuelStation.Zone refill(Car car) {
        return PowerSource.BATTERY.equals(car.getPowerSource()) ? FuelStation.Zone.BATTERY : FuelStation.Zone.UNKNOWN;
    }

    public enum Zone {
        BATTERY,
        UNKNOWN
    }
}
