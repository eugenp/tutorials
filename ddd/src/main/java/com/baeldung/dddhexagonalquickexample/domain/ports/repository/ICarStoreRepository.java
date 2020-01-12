package com.baeldung.dddhexagonalquickexample.domain.ports.repository;

import com.baeldung.dddhexagonalquickexample.domain.CarStore;

public interface ICarStoreRepository {

	CarStore findStoreById(Long idStore);
	
	void saveStore(CarStore store);
}
