package com.baeldung.spring.jinq.repositories;

import java.util.List;
import java.util.Optional;

import org.jinq.tuples.Pair;
import org.jinq.tuples.Tuple3;

import com.baeldung.spring.jinq.entities.Car;
import com.baeldung.spring.jinq.entities.Manufacturer;

public interface CarRepository {

    Optional<Car> findByModel(String model);

    List<Car> findByModelAndDescription(String model, String desc);

    List<Tuple3<String, Integer, String>> findWithModelYearAndEngine();

    Optional<Manufacturer> findManufacturerByModel(String model);

    List<Pair<Manufacturer, Car>> findCarsPerManufacturer();

    long countCarsByModel(String model);

    List<Car> findAll(int skip, int limit);
}
