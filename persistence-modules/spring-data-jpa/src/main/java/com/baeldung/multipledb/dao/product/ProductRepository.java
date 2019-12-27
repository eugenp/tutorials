package com.baeldung.multipledb.dao.product;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.baeldung.multipledb.model.product.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {


    List<Product> findAllByPrice(double price, Pageable pageable);
}
