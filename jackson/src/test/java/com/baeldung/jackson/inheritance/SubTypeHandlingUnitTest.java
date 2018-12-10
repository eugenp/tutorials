package com.baeldung.jackson.inheritance;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SubTypeHandlingUnitTest {
    @Test
    public void givenSubTypes_whenConvertingObjects_thenDataValuesArePreserved() {
        ObjectMapper mapper = new ObjectMapper();

        SubTypeConversionStructure.Car car = new SubTypeConversionStructure.Car("Mercedes-Benz", "S500", 5, 250.0);
        SubTypeConversionStructure.Truck truck = mapper.convertValue(car, SubTypeConversionStructure.Truck.class);

        assertEquals("Mercedes-Benz", truck.getMake());
        assertEquals("S500", truck.getModel());
    }

    @Test
    public void givenSubType_whenNotUsingNoArgsConstructors_thenSucceed() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enableDefaultTyping();

        SubTypeConstructorStructure.Car car = new SubTypeConstructorStructure.Car("Mercedes-Benz", "S500", 5, 250.0);
        SubTypeConstructorStructure.Truck truck = new SubTypeConstructorStructure.Truck("Isuzu", "NQR", 7500.0);

        List<SubTypeConstructorStructure.Vehicle> vehicles = new ArrayList<>();
        vehicles.add(car);
        vehicles.add(truck);

        SubTypeConstructorStructure.Fleet serializedFleet = new SubTypeConstructorStructure.Fleet();
        serializedFleet.setVehicles(vehicles);

        String jsonDataString = mapper.writeValueAsString(serializedFleet);
        mapper.readValue(jsonDataString, SubTypeConstructorStructure.Fleet.class);
    }
}