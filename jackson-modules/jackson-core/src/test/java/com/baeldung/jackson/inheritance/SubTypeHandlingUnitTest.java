package com.baeldung.jackson.inheritance;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.baeldung.jackson.inheritance.SubTypeConstructorStructure.Car;
import com.baeldung.jackson.inheritance.SubTypeConstructorStructure.Fleet;
import com.baeldung.jackson.inheritance.SubTypeConstructorStructure.Truck;
import com.baeldung.jackson.inheritance.SubTypeConstructorStructure.Vehicle;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;

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
        PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder()
            .allowIfSubType("com.baeldung.jackson.inheritance")
            .allowIfSubType("java.util.ArrayList")
            .build();
        mapper.activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.NON_FINAL);
        
        Car car = new Car("Mercedes-Benz", "S500", 5, 250.0);
        Truck truck = new Truck("Isuzu", "NQR", 7500.0);

        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(car);
        vehicles.add(truck);

        Fleet serializedFleet = new Fleet();
        serializedFleet.setVehicles(vehicles);

        String jsonDataString = mapper.writeValueAsString(serializedFleet);
        mapper.readValue(jsonDataString, Fleet.class);

        Fleet deserializedFleet = mapper.readValue(jsonDataString, Fleet.class);

        assertThat(deserializedFleet.getVehicles().get(0), instanceOf(Car.class));
        assertThat(deserializedFleet.getVehicles().get(1), instanceOf(Truck.class));
    }
}