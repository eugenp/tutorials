package com.baeldung.graphqlreturnmap.repository;


import com.baeldung.graphqlreturnmap.entity.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> getProducts(Integer pageSize, Integer pageNumber);
    Product getProduct(Integer id);

}
