package com.baeldung.listmethod;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class CarUnitTest {

    @Test
    void whenCarList_thenUseForLoop() {
        List<Car> cars = new ArrayList<>();
        cars.add(new Car("Ford"));
        cars.add(new Car("BMW"));
        cars.add(new Car("Toyota"));

        List<String> names = new ArrayList<>();

        for (int i = 0; i < cars.size(); i++) {
            Car c = cars.get(i);
            names.add(c.getName());
        }

        assertEquals("Ford", names.get(0));
        assertEquals("BMW", names.get(1));
        assertEquals("Toyota", names.get(2));
    }
    @Test
    void whenCarList_thenUseForEachLoop() {
        List<Car> cars = new ArrayList<>();
        cars.add(new Car("Ford"));
        cars.add(new Car("BMW"));
        cars.add(new Car("Toyota"));

        List<String> names = new ArrayList<>();

        for (Car c : cars) {
            names.add(c.getName());
        }

        assertEquals("Ford", names.get(0));
        assertEquals("BMW", names.get(1));
        assertEquals("Toyota", names.get(2));
    }

    @Test
    void whenCarList_thenUseListForEachLoop() {
        List<Car> cars = new ArrayList<>();
        cars.add(new Car("Ford"));
        cars.add(new Car("BMW"));
        cars.add(new Car("Toyota"));

        List<String> names = new ArrayList<>();

        cars.forEach(c -> names.add(c.getName()));

        assertEquals("Ford", names.get(0));
        assertEquals("BMW", names.get(1));
        assertEquals("Toyota", names.get(2));
    }

    @Test
    void whenCarList_thenUseStream() {
        List<Car> cars = new ArrayList<>();
        cars.add(new Car("Ford"));
        cars.add(new Car("BMW"));
        cars.add(new Car("Toyota"));

        List<String> names = cars
                .stream()
                .map(Car::getName)
                .collect(Collectors.toList());

        assertEquals("Ford", names.get(0));
        assertEquals("BMW", names.get(1));
        assertEquals("Toyota", names.get(2));
    }
}