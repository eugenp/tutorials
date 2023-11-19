package com.baeldung.graphqlvsrest.repository;

import com.baeldung.graphqlvsrest.entity.Product;
import com.baeldung.graphqlvsrest.model.ProductModel;

import java.util.List;

public interface ProductRepository {
    List<Product> getProducts(Integer pageSize, Integer pageNumber);
    Product getProduct(Integer id);
    Product save(ProductModel productModel);
    Product update(Integer productId, ProductModel productModel);

}
