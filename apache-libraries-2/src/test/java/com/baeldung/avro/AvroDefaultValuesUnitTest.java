package com.baeldung.avro;

import generated.avro.Car;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AvroDefaultValuesUnitTest {

    @Test
    public void givenCarJsonSchema_whenCarIsSerialized_thenCarIsSuccessfullyDeserialized() {

        Car car1 = new Car();
        car1.setColor("blue");

        try {

            SerializationDeserializationLogic.serializeCar(car1);
            Car deserializedCar1 = SerializationDeserializationLogic.deserializeCar();

            assertNull(deserializedCar1.getBrand());
            assertNull(deserializedCar1.getNumberOfDoors());
            assertEquals("blue", deserializedCar1.getColor().toString());
            assertEquals(0, deserializedCar1.getNumberOfWheels());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
