package com.baeldung.injectiontypes.javaconfig.domain.constructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class SolarSystemConstructorDi {

    private Moon moon;
    private Earth earth;

    @Autowired
    public SolarSystemConstructorDi(Moon moon, Earth earth) {
        this.moon = moon;
        this.earth = earth;
    }

    public void printDiameters() {
        System.out.println("Diameter of the uranus is :" + moon.getDiameter() + " km");
        System.out.println("Diameter of the jupiter is :" + earth.getDiameter() + " km");
    }

    public Moon getMoon() {
        return moon;
    }

    public void setMoon(Moon moon) {
        this.moon = moon;
    }

    public Earth getEarth() {
        return earth;
    }

    public void setEarth(Earth earth) {
        this.earth = earth;
    }
}
