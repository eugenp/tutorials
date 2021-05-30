package com.baeldung.architecture.hexagonal.repo;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductRepository extends CrudRepository<ProductRow, String> {

  Optional<ProductRow> findById(String id);
}
