package com.baeldung.hexagonaldraft.port.outbound;

import java.util.List;
import java.util.Optional;

import com.baeldung.hexagonaldraft.domain.model.Product;


/**
 * The repository interface is an outbound port.
 */
public interface ProductRepository {

    List<Product> getProducts();

    Optional<Product> getProductById(Integer productId);

    Product addProduct(Product product);

    Optional<Product> removeProduct(Integer productId);
}
