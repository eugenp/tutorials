package com.baeldung.injectiontypes.xmlconfig.setter;


import com.baeldung.injectiontypes.domain.Jupiter;

public class SolarSystemSetterDi {

    private Jupiter jupiter;

    public void printDiameters() {
        System.out.println("Diameter of the jupiter is :" + jupiter.getDiameter() + " km");
    }

    public void setJupiter(Jupiter jupiter) {
        this.jupiter = jupiter;
    }
}
