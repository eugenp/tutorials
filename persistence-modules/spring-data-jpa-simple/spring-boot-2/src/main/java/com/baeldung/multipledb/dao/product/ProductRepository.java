package com.baeldung.multipledb.dao.product;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.baeldung.multipledb.model.product.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product, Integer>, CrudRepository<Product, Integer> {

    List<Product> findAllByPrice(double price, Pageable pageable);
}
