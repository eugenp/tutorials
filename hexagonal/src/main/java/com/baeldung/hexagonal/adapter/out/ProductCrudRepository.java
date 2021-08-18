package com.baeldung.hexagonal.adapter.out;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.hexagonal.domain.ports.ProductRepository;

@Repository
public interface ProductCrudRepository extends CrudRepository<Product, Integer>, ProductRepository {

  Product findByName(String name);
}
