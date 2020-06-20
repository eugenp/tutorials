package com.baeldung.hexagonal.server;

import com.baeldung.hexagonal.domain.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CarServerAdapterTest {

    private CarServerAdapter classUnderTest;

    @BeforeEach
    void setUp() {
        classUnderTest = new CarServerAdapter();
    }

    @Test
    void whenSaved_thenAddedToList() {
        Car car = new Car("BL-DN-G", "Bealdung");

        classUnderTest.saveCar(car);

        List<Car> cars = classUnderTest.getCars();
        assertEquals(1, cars.size());
        assertEquals("BL-DN-G", cars.get(0).getLicensePlate());
    }

    @Test
    void givenAlreadyOneCarAdded_whenRequested_thenReturned() {
        Car car = new Car("BL-DN-G", "Bealdung");
        classUnderTest.saveCar(car);

        List<Car> cars = classUnderTest.getCars();

        assertEquals(1, cars.size());
        assertEquals("BL-DN-G", cars.get(0).getLicensePlate());
    }

    @Test
    void givenEmptyList_whenRequested_thenEmptyListReturned() {
        List<Car> cars = classUnderTest.getCars();

        assertEquals(0, cars.size());
    }

}