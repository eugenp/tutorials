package com.baeldung.redistestcontainers.repository;

import com.baeldung.redistestcontainers.hash.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, String> {
}
