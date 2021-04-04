package com.baeldung.hexagonalarchitecture.port;

import java.util.List;

import com.baeldung.hexagonalarchitecture.domain.model.Product;

/**
 * The repository interface is an outbound port that enables communication from the core application to a database
 */
public interface ProductRepository {

    List<Product> getProducts();

    Product getProductById(Integer productId);

    Product addProduct(Product product);

    Product removeProduct(Integer productId);
}
