package com.baeldung.hexagonal.architecture.port;

import com.baeldung.hexagonal.architecture.domain.model.Product;

import java.util.List;

/**
 * The repository interface is an outbound port that enables communication from the core application to a database
 */
public interface ProductRepository {

    List<Product> getProducts();

    Product getProductById(Integer productId);

    Product addProduct(Product product);

    Product removeProduct(Integer productId);
}
