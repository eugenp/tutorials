package com.baeldung.injectiontypes.xmlconfig.constructor;

import com.baeldung.injectiontypes.domain.Moon;

public class SolarSystemConstructorDi {

    private Moon moon;

    public SolarSystemConstructorDi(Moon moon) {
        this.moon = moon;
    }

    public void printDiameters() {
        System.out.println("Diameter of the moon is :" + moon.getDiameter() + " km");
    }
}
