package com.baeldung.graphqlvsrest.repository;

import java.util.List;

import com.baeldung.graphqlvsrest.entity.Product;
import com.baeldung.graphqlvsrest.model.ProductModel;

public interface ProductRepository {
    List<Product> getProducts(Integer pageSize, Integer pageNumber);
    Product getProduct(Integer id);
    Product save(ProductModel productModel);
    Product update(Integer productId, ProductModel productModel);

}
