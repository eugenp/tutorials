package com.baeldung.dependencyinjectiontypes.model;

import com.baeldung.dependencyinjectiontypes.annotation.CarQualifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CarHandler {

    @Autowired
    @CarQualifier
    private List<Vehicle> vehicles;

    public List<Vehicle> getVehicles() throws NoSuchFieldException {
        ResolvableType vehiclesType = ResolvableType.forField(getClass().getDeclaredField("vehicles"));
        System.out.println(vehiclesType);
        ResolvableType type = vehiclesType.getGeneric();
        System.out.println(type);
        Class<?> aClass = type.resolve();
        System.out.println(aClass);
        return this.vehicles;
    }
}
