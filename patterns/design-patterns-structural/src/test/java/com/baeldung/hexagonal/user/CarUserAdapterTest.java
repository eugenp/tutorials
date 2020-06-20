package com.baeldung.hexagonal.user;

import com.baeldung.hexagonal.domain.Car;
import com.baeldung.hexagonal.domain.CarService;
import com.baeldung.hexagonal.util.TestAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CarUserAdapterTest {

    private CarUserAdapter classUnderTest;
    private TestAdapter testAdapter;

    @BeforeEach
    void setUp() {
        testAdapter = new TestAdapter();
        classUnderTest = new CarUserAdapter(new CarService(testAdapter));
    }

    @Test
    void whenCarSubmitted_ThenSavedSuccessful() {
        Car car = new Car("BL-DN-G", "Bealdung");

        classUnderTest.submitCar(car);

        assertEquals(1, testAdapter.getCars().size());
        assertEquals("BL-DN-G", testAdapter.getCars().get(0).getLicensePlate());
        assertEquals("Bealdung", testAdapter.getCars().get(0).getName());
    }

    @Test
    void whenCarsRetrieved_ThenReturnedSuccessful() {
        Car car = new Car("BL-DN-G", "Bealdung");
        testAdapter.saveCar(car);

        List<Car> cars = classUnderTest.retrieveCars();

        assertEquals(1, cars.size());
        assertEquals("BL-DN-G", cars.get(0).getLicensePlate());
        assertEquals("Bealdung", cars.get(0).getName());
    }

}