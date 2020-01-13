package com.baeldung.dddhexagonalquickexample.business.adapter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.baeldung.dddhexagonalquickexample.domain.Car;
import com.baeldung.dddhexagonalquickexample.domain.CarStore;
import com.baeldung.dddhexagonalquickexample.domain.ports.repository.ICarStoreRepository;

public class CustomCarStoreServiceTest{

    private ICarStoreRepository repository;
    private CustomCarStoreService service;

    @BeforeEach
    void setUp(){
        repository = mock(ICarStoreRepository.class);
        service = new CustomCarStoreService(repository);
    }

    @Test
    void whenCreateStore_thenOk() throws Exception {
        // given
        CarStore store = new CarStore();
        doNothing().when(repository).saveStore(store);

        // when
        service.createStore(store);

        // then
        verify(repository, times(1)).saveStore(store);
    }

    @Test
    void whenGetStore_thenOk() throws Exception {
        // given
        Long idStore = 1L;
        CarStore store = new CarStore();
        doReturn(store).when(repository).findStoreById(idStore);

        // when
        CarStore result = service.getStore(idStore);

        // then
        verify(repository, times(1)).findStoreById(idStore);
        assertThat(result).isEqualTo(store);
    }

    @Test
    void whenGetCar_thenOk() throws Exception {
        // given
        Long idStore = 1L;
        Long idCar = 1L;
        CarStore store = new CarStore();
        store.setCatalog(new ArrayList<Car>());
        Car car = new Car();
        car.setId(idCar);
        store.addCarToCatalog(car);
        doReturn(store).when(repository).findStoreById(idStore);

        // when
        Car result = service.getCar(idStore, idCar);

        // then
        verify(repository, times(1)).findStoreById(idStore);
        assertThat(result).isEqualTo(car);
    }

    @Test
    void whenGetCar_thenNotExists() throws Exception {
        // given
        Long idStore = 1L;
        Long idCar = 1L;
        CarStore store = new CarStore();
        store.setCatalog(new ArrayList<Car>());
        Car car = new Car();
        car.setId(2L);
        store.addCarToCatalog(car);
        doReturn(store).when(repository).findStoreById(idStore);

        // when/then
        assertThrows(NoSuchElementException.class, () -> {
            service.getCar(idStore, idCar);
        });
    }

    @Test
    void whenGetCars_thenOk() throws Exception {
        // given
        Long idStore = 1L;
        CarStore store = new CarStore();
        doReturn(store).when(repository).findStoreById(idStore);

        // when
        List<Car> result = service.getCars(idStore);

        // then
        verify(repository, times(1)).findStoreById(idStore);
        assertThat(result).isEqualTo(store.getCatalog());
    }

    @Test
    void whenSellCar_thenOk() throws Exception {
        // given
        Long idStore = 1L;
        Long idCar = 1L;
        CarStore store = new CarStore();
        store.setCatalog(new ArrayList<Car>());
        store.setMoney(0.0);
        Car car = new Car();
        car.setId(idCar);
        car.setPrice(1000.0);
        store.addCarToCatalog(car);
        doReturn(store).when(repository).findStoreById(idStore);
        doNothing().when(repository).saveStore(store);

        // when
        service.sellCar(idStore, idCar);

        // then
        verify(repository, times(1)).findStoreById(idStore);
        verify(repository, times(1)).saveStore(store);
    }

    @Test
    void whenSellCar_thenNotExists() throws Exception {
        // given
        Long idStore = 1L;
        Long idCar = 1L;
        CarStore store = new CarStore();
        store.setCatalog(new ArrayList<Car>());
        store.setMoney(0.0);
        Car car = new Car();
        car.setId(2L);
        store.addCarToCatalog(car);
        doReturn(store).when(repository).findStoreById(idStore);

        // when/then
        assertThrows(NoSuchElementException.class, () -> {
            service.sellCar(idStore, idCar);
        });
    }

    @Test
    void whenAddCarToCatalog_thenOk() throws Exception {
        // given
        Long idStore = 1L;
        Long idCar = 1L;
        CarStore store = new CarStore();
        store.setCatalog(new ArrayList<Car>());
        Car car = new Car();
        car.setId(idCar);
        car.setPrice(1000.0);
        doReturn(store).when(repository).findStoreById(idStore);
        doNothing().when(repository).saveStore(store);

        // when
        service.addCarToCatalog(idStore, car);

        // then
        verify(repository, times(1)).findStoreById(idStore);
        verify(repository, times(1)).saveStore(store);
    }
}
