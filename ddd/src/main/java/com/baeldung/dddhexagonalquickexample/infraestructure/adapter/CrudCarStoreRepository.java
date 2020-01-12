package com.baeldung.dddhexagonalquickexample.infraestructure.adapter;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.dddhexagonalquickexample.domain.CarStore;

@Repository
public interface CrudCarStoreRepository extends CrudRepository<CarStore, Long> {
}
