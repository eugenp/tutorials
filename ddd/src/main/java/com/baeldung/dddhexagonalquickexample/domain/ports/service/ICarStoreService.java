package com.baeldung.dddhexagonalquickexample.domain.ports.service;

import java.util.List;

import com.baeldung.dddhexagonalquickexample.domain.Car;
import com.baeldung.dddhexagonalquickexample.domain.CarStore;

public interface ICarStoreService {

    void createStore(CarStore store);

    CarStore getStore(Long idStore);

    Car getCar(Long idStore, Long idCar);

    List<Car> getCars(Long idStore);

    void sellCar(Long idStore, Long idCar);

    void addCarToCatalog(Long idStore, Car car);
}
