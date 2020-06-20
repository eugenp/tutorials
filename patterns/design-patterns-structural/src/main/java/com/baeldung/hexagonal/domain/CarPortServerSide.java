package com.baeldung.hexagonal.domain;

import java.util.List;

/**
 * This interface is used as a port to the serverside.
 */
public interface CarPortServerSide {

    void saveCar(Car car);

    List<Car> getCars();
}
