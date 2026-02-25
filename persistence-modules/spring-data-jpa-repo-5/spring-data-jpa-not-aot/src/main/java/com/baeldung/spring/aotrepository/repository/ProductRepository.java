package com.baeldung.spring.aotrepository.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.spring.aotrepository.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product save(Product product);

    @Transactional(readOnly = true)
    List<Product> findAll();

    List<Product> findAllById(Iterable<Long> longs);

    @Query(value = "SELECT * FROM PRODUCTS", nativeQuery = true)
    List<Product> nativeQueryFindAllProducts();

    @Query(value = "SELECT p FROM Product p")
    List<Product> queryFindAllProducts();
}
