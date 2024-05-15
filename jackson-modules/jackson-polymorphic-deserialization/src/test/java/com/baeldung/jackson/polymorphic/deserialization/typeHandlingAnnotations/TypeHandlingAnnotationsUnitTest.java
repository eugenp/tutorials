package com.baeldung.jackson.polymorphic.deserialization.typeHandlingAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TypeHandlingAnnotationsUnitTest {

    @Test
    public void whenDeserializingPolymorphic_thenCorrect() throws JsonProcessingException {
        String json = "{\"type\":\"ELECTRIC_VEHICLE\",\"autonomy\":\"500\",\"chargingTime\":\"200\"}";

        Vehicle vehicle = new ObjectMapper().readerFor(Vehicle.class).readValue(json);

        assertEquals(Vehicle.ElectricVehicle.class, vehicle.getClass());
    }
}
