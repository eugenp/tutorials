package com.article.hexagonal.architecture.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.article.hexagonal.architecture.model.Product;

/**
 * @author AshwiniKeshri
 *
 */

public interface ProductRepository extends JpaRepository<Product, Long> {

}
