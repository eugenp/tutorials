package com.baeldung.hexagonal;

import org.springframework.data.repository.CrudRepository;

import java.util.stream.Stream;

public interface ProductRepository extends CrudRepository<Product, Long> {

    Stream<Product> findByName(String name);
}