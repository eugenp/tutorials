package com.baeldung.multipledb.dao.product;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.baeldung.multipledb.model.product.ProductMultipleDB;

public interface ProductRepository extends PagingAndSortingRepository<ProductMultipleDB, Integer> {

}