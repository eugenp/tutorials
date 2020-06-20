package com.baeldung.hexagonal.domain;

import com.baeldung.hexagonal.util.TestAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CarServiceTest {

    private TestAdapter testAdapter;
    private CarService classUnderTest;

    @BeforeEach
    void setUp() {
        testAdapter = new TestAdapter();
        classUnderTest = new CarService(testAdapter);
    }

    @Test
    void givenValidCar_WhenSubmitting_ThenSavedSuccessfully() {
        Car car = new Car("BL-DN-G", "Bealdung");

        classUnderTest.createCar(car);

        assertEquals(1, testAdapter.getCars().size());
        assertEquals("BL-DN-G", testAdapter.getCars().get(0).getLicensePlate());
        assertEquals("Bealdung", testAdapter.getCars().get(0).getName());
    }

    @Test
    void givenCarStores_WhenRetrieving_ThenReturnedSuccessfully() {
        Car car = new Car("BL-DN-G", "Bealdung");
        testAdapter.saveCar(car);

        List<Car> cars = classUnderTest.getCars();

        assertEquals(1, cars.size());
        assertEquals("BL-DN-G", cars.get(0).getLicensePlate());
        assertEquals("Bealdung", cars.get(0).getName());
    }

}