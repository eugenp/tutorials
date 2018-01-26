package com.baeldung.injectiontypes.javaconfig.setter;

import com.baeldung.injectiontypes.domain.Jupiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class SolarSystemSetterDi {

    private Jupiter jupiter;

    public void printDiameters() {
        System.out.println("Diameter of the jupiter is :" + jupiter.getDiameter() + " km");
    }

    @Autowired
    public void setJupiter(Jupiter jupiter) {
        this.jupiter = jupiter;
    }
}
