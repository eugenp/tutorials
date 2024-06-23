package com.baeldung.avro;

import generated.avro.Car;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class AvroDefaultValuesUnitTest {

    @Test
    public void givenCarJsonSchema_whenCarIsSerialized_thenCarIsSuccessfullyDeserialized() throws IOException {

        Car car = new Car();
        car.setColor("blue");

        SerializationDeserializationLogic.serializeCar(car);
        Car deserializedCar1 = SerializationDeserializationLogic.deserializeCar();

        assertNull(deserializedCar1.getBrand());
        assertNull(deserializedCar1.getNumberOfDoors());
        assertEquals("blue", deserializedCar1.getColor().toString());
        assertEquals(0, deserializedCar1.getNumberOfWheels());
    }
}
