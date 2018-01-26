package com.baeldung.injectiontypes.javaconfig.constructor;

import com.baeldung.injectiontypes.domain.Moon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class SolarSystemConstructorDi {

    private Moon moon;

    @Autowired
    public SolarSystemConstructorDi(Moon moon) {
        this.moon = moon;
    }

    public void printDiameters() {
        System.out.println("Diameter of the moon is :" + moon.getDiameter() + " km");
    }
}
