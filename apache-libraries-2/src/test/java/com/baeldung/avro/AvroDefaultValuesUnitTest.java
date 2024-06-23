package com.baeldung.avro;

import generated.avro.Car;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class AvroDefaultValuesUnitTest {

    @Test
    public void givenCarJsonSchema_whenCarIsSerialized_thenCarIsSuccessfullyDeserialized() {

        Car car = new Car();
        car.setColor("blue");

        SerializationDeserializationLogic.serializeCar(car);
        Car deserializedCar = SerializationDeserializationLogic.deserializeCar();

        assertNull(deserializedCar.getBrand());
        assertNull(deserializedCar.getNumberOfDoors());
        assertEquals("blue", deserializedCar.getColor().toString());
        assertEquals(0, deserializedCar.getNumberOfWheels());
    }
}
