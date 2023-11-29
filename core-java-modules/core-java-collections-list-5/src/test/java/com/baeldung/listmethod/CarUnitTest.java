package com.baeldung.listmethod;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static org.junit.jupiter.api.Assertions.*;


class CarUnitTest {

    private static final Logger logger = LoggerFactory.getLogger(CarUnitTest.class);

    @ParameterizedTest
    @EnabledIfEnvironmentVariable(named="PERFORMANCE_TEST", matches="true")
    @ValueSource(ints = {1, 1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000})
    void whenCarList_thenUseForLoop(int iterations) {
        List<Car> cars = buildList(iterations);

        List<String> names = new ArrayList<>();

        long start = System.nanoTime();
        for (int i = 0; i < cars.size(); i++) {
            Car c = cars.get(i);
            names.add(c.getName());
        }

        long end = System.nanoTime();
        logger.info("Time taken for {} - {}", iterations, end - start);
        assertEquals(iterations, names.size());
    }

    @ParameterizedTest
    @EnabledIfEnvironmentVariable(named="PERFORMANCE_TEST", matches="true")
    @ValueSource(ints = {1, 1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000})
    void whenCarList_thenUseForEachLoop(int iterations) {
        List<Car> cars = buildList(iterations);

        List<String> names = new ArrayList<>();

        long start = System.nanoTime();
        for (Car c : cars) {
            names.add(c.getName());
        }
        long end = System.nanoTime();
        logger.info("Time taken for {} - {}", iterations, end - start);
        assertEquals(iterations, names.size());
    }

    @ParameterizedTest
    @EnabledIfEnvironmentVariable(named="PERFORMANCE_TEST", matches="true")
    @ValueSource(ints = {1, 1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000})
    void whenCarList_thenUseListForEachLoop(int iterations) {
        List<Car> cars = buildList(iterations);

        List<String> names = new ArrayList<>();

        long start = System.nanoTime();
        cars.forEach(c -> names.add(c.getName()));

        long end = System.nanoTime();
        logger.info("Time taken for {} - {}", iterations, end - start);
        assertEquals(iterations, names.size());
    }

    @ParameterizedTest
    @EnabledIfEnvironmentVariable(named="PERFORMANCE_TEST", matches="true")
    @ValueSource(ints = {1, 1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000})
    void whenCarList_thenUseStream(int iterations) {
        List<Car> cars = buildList(iterations);

        long start = System.nanoTime();
        List<String> names = cars.stream()
            .map(Car::getName)
            .collect(Collectors.toList());

        long end = System.nanoTime();
        logger.info("Time taken for {} - {}", iterations, end - start);
        assertEquals(iterations, names.size());
    }

    @ParameterizedTest
    @EnabledIfEnvironmentVariable(named="PERFORMANCE_TEST", matches="true")
    @ValueSource(ints = {1, 1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000})
    void whenCarList_thenUseParallelStream(int iterations) {
        List<Car> cars = buildList(iterations);

        long start = System.nanoTime();
        List<String> names = cars.parallelStream()
            .map(Car::getName)
            .collect(Collectors.toList());

        long end = System.nanoTime();
        logger.info("Time taken for {} - {}", iterations, end - start);
        assertEquals(iterations, names.size());
    }

    @ParameterizedTest
    @EnabledIfEnvironmentVariable(named="PERFORMANCE_TEST", matches="true")
    @ValueSource(ints = {1, 1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000})
    void whenCarList_thenUseForLoopWithLogic(int iterations) {
        List<Car> cars = buildList(iterations);

        List<String> names = new ArrayList<>();

        long start = System.nanoTime();
        for (int i = 0; i < cars.size(); i++) {
            Car c = cars.get(i);
            if(c.getName().startsWith("A") || c.getName().startsWith("B")) {
                continue;
            }
            names.add(c.getName().toUpperCase());
        }

        long end = System.nanoTime();
        logger.info("Time taken for {} - {}", iterations, end - start);
    }

    @ParameterizedTest
    @EnabledIfEnvironmentVariable(named="PERFORMANCE_TEST", matches="true")
    @ValueSource(ints = {1, 1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000})
    void whenCarList_thenUseForEachLoopWithLogic(int iterations) {
        List<Car> cars = buildList(iterations);

        List<String> names = new ArrayList<>();

        long start = System.nanoTime();
        for (Car c : cars) {
            if(c.getName().startsWith("A") || c.getName().startsWith("B")) {
                continue;
            }
            names.add(c.getName().toUpperCase());
        }
        long end = System.nanoTime();
        logger.info("Time taken for {} - {}", iterations, end - start);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000})
    @EnabledIfEnvironmentVariable(named="PERFORMANCE_TEST", matches="true")
    void whenCarList_thenUseListForEachLoopWithLogic(int iterations) {
        List<Car> cars = buildList(iterations);

        List<String> names = new ArrayList<>();

        long start = System.nanoTime();
        cars.forEach(c -> {
            if(c.getName().startsWith("A") || c.getName().startsWith("B")) {
                return;
            }
            names.add(c.getName().toUpperCase());
        });

        long end = System.nanoTime();
        logger.info("Time taken for {} - {}", iterations, end - start);
    }

    @ParameterizedTest
    @EnabledIfEnvironmentVariable(named="PERFORMANCE_TEST", matches="true")
    @ValueSource(ints = {1, 1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000})
    void whenCarList_thenUseStreamWithLogic(int iterations) {
        List<Car> cars = buildList(iterations);

        long start = System.nanoTime();
        List<String> names = cars.stream()
            .map(Car::getName)
            .filter(name -> !name.startsWith("A"))
            .filter(name -> !name.startsWith("B"))
            .map(String::toUpperCase)
            .collect(Collectors.toList());

        long end = System.nanoTime();
        logger.info("Time taken for {} - {}", iterations, end - start);
    }

    @ParameterizedTest
    @EnabledIfEnvironmentVariable(named="PERFORMANCE_TEST", matches="true")
    @ValueSource(ints = {1, 1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000})
    void whenCarList_thenUseParallelStreamWithLogic(int iterations) {
        List<Car> cars = buildList(iterations);

        long start = System.nanoTime();
        List<String> names = cars.parallelStream()
            .map(Car::getName)
            .filter(name -> !name.startsWith("A"))
            .filter(name -> !name.startsWith("B"))
            .map(String::toUpperCase)
            .collect(Collectors.toList());

        long end = System.nanoTime();
        logger.info("Time taken for {} - {}", iterations, end - start);
    }



    private List<Car> buildList(int size) {
        List<Car> cars = new ArrayList<>();
        for(int i = 0; i < size; i++) {
            cars.add(new Car(RandomStringUtils.random(5)));
        }

        return cars;
    }
}