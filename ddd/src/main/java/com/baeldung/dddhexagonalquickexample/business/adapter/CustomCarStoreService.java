package com.baeldung.dddhexagonalquickexample.business.adapter;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baeldung.dddhexagonalquickexample.domain.Car;
import com.baeldung.dddhexagonalquickexample.domain.CarStore;
import com.baeldung.dddhexagonalquickexample.domain.ports.repository.ICarStoreRepository;
import com.baeldung.dddhexagonalquickexample.domain.ports.service.ICarStoreService;

@Service
public class CustomCarStoreService implements ICarStoreService{

    private ICarStoreRepository carStoreRepository;

    public CustomCarStoreService(ICarStoreRepository carStoreRepository) {
        this.carStoreRepository = carStoreRepository;
    }

    public void createStore(CarStore store) {
        carStoreRepository.saveStore(store);
    }

    public CarStore getStore(Long idStore) {
        return carStoreRepository.findStoreById(idStore);
    }

    public Car getCar(Long idStore, Long idCar) {
        return carStoreRepository.findStoreById(idStore).getCar(idCar);
    }

    public List<Car> getCars(Long idStore){
        return carStoreRepository.findStoreById(idStore).getCatalog();
    }

    public void sellCar(Long idStore, Long idCar) {
        CarStore carStore = carStoreRepository.findStoreById(idStore);
        carStore.sellCar(idCar);
        carStoreRepository.saveStore(carStore);
    }

    public void addCarToCatalog(Long idStore, Car car) {
        CarStore carStore = carStoreRepository.findStoreById(idStore);
        car.setStore(carStore);
        carStore.addCarToCatalog(car);
        carStoreRepository.saveStore(carStore);
    }
}
