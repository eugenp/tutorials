package com.baeldung.dddhexagonalquickexample.infraestructure.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.dddhexagonalquickexample.domain.CarStore;
import com.baeldung.dddhexagonalquickexample.domain.ports.repository.ICarStoreRepository;
import com.baeldung.dddhexagonalquickexample.infraestructure.adapter.CrudCarStoreRepository;

@Component
public class CarStoreRepository implements ICarStoreRepository {
	
	@Autowired
	private CrudCarStoreRepository carStoreRepository;

	@Override
	public CarStore findStoreById(Long idStore) {
		return carStoreRepository.findById(idStore).get();
	}
	@Override
	public void saveStore(CarStore store) {
		carStoreRepository.save(store);
	}
	

	public void setCarStoreRepository(CrudCarStoreRepository carStoreRepository) {
		this.carStoreRepository = carStoreRepository;
	}
}