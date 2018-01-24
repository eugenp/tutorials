package com.baeldung.injectiontypes.javaconfig.domain.setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class SolarSystemSetterDi {

    private Uranus uranus;
    private Jupiter jupiter;

    public void printDiameters() {
        System.out.println("Diameter of the uranus is :" + uranus.getDiameter() + " km");
        System.out.println("Diameter of the jupiter is :" + jupiter.getDiameter() + " km");
    }

    @Autowired
    public void setUranus(Uranus uranus) {
        this.uranus = uranus;
    }

    @Autowired
    public void setJupiter(Jupiter jupiter) {
        this.jupiter = jupiter;
    }
}
