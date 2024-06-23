package com.baeldung.avro;

import generated.avro.Car;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AvroDefaultValuesUnitTest {

    @Test
    public void givenCarJsonSchema_whenCarIsSerialized_thenCarIsSuccessfullyDeserialized() {

        Car car = Car.newBuilder().setColor("blue").build();

        SerializationDeserializationLogic.serializeCar(car);
        Car deserializedCar = SerializationDeserializationLogic.deserializeCar();

        assertEquals("Dacia", deserializedCar.getBrand());
        assertEquals(4,deserializedCar.getNumberOfDoors());
        assertEquals("blue", deserializedCar.getColor().toString());
    }
}
