package com.baeldung.injectiontypes.xmlconfig.domain.setter;


public class SolarSystemSetterDi {

    private Uranus uranus;
    private Jupiter jupiter;

    public Uranus getUranus() {
        return uranus;
    }

    public void printDiameters() {
        System.out.println("Diameter of the uranus is :" + uranus.getDiameter() + " km");
        System.out.println("Diameter of the jupiter is :" + jupiter.getDiameter() + " km");
    }

    public void setUranus(Uranus uranus) {
        this.uranus = uranus;
    }

    public Jupiter getJupiter() {
        return jupiter;
    }

    public void setJupiter(Jupiter jupiter) {
        this.jupiter = jupiter;
    }
}
