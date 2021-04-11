package com.kelaskoding.repositories;

import com.kelaskoding.entities.Product;

import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long>{
    
}
