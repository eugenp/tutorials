package com.baeldung.spring.aotrepository.repository;

import com.baeldung.spring.aotrepository.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product save(Product product);

    @Transactional(readOnly = true)
    List<Product> findAll();

    List<Product> findAllById(Iterable<Long> longs);

    List<Product> findByPriceGreaterThan(double price);

    @Query(value = "SELECT * FROM PRODUCTS", nativeQuery = true)
    List<Product> nativeQueryFindAllProducts();

    @Query(value = "SELECT p FROM Product p")
    List<Product> queryFindAllProducts();

    void delete(Product entity);
}
