package com.baeldung.hexagonal.springapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.baeldung.hexagonal.core.ProductRepository;
import com.baeldung.hexagonal.springapp.entity.ProductEntity;

public interface JpaProductRepository extends ProductRepository, CrudRepository<ProductEntity, Long> {

}
