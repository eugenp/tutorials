package com.baeldung.hexagonal.domain;

import java.util.List;

public interface CarPortServerSide {

    void saveCar(Car car);

    List<Car> getCars();
}
