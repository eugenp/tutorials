package com.baeldung.jpa.paginationsorting.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.baeldung.jpa.paginationsorting.model.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product, Integer>, CrudRepository<Product, Integer> {

    List<Product> findAllByPrice(double price, Pageable pageable);

}
