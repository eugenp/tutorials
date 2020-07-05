package com.baeldung.hexagonal.architecture.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.hexagonal.architecture.model.Product;

/**
 * @author AshwiniKeshri
 *
 */

public interface ProductRepository extends JpaRepository<Product, Long> {

}
