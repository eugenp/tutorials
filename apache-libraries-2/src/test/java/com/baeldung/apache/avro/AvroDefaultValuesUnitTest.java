package com.baeldung.apache.avro;

import com.baeldung.apache.avro.generated.Car;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class AvroDefaultValuesUnitTest {

    @Test
    public void givenCarJsonSchema_whenCarIsSerialized_thenCarIsSuccessfullyDeserialized() throws IOException {

        Car car = Car.newBuilder()
            .build();

        SerializationDeserializationLogic.serializeCar(car);
        Car deserializedCar = SerializationDeserializationLogic.deserializeCar();

        assertEquals("Dacia", deserializedCar.getBrand());
        assertEquals(4, deserializedCar.getNumberOfDoors());
        assertNull(deserializedCar.getColor());
    }
}
