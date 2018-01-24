package com.baeldung.injectiontypes.xmlconfig.domain.constructor;

public class SolarSystemConstructorDi {

    private Moon moon;
    private Earth earth;

    public SolarSystemConstructorDi(Moon moon, Earth earth) {
        this.moon = moon;
        this.earth = earth;
    }

    public void printDiameters() {
        System.out.println("Diameter of the moon is :" + moon.getDiameter() + " km");
        System.out.println("Diameter of the earth is :" + earth.getDiameter() + " km");
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
