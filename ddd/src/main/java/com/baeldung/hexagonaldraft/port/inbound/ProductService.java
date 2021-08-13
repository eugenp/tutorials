package com.baeldung.hexagonaldraft.port.inbound;

import java.util.List;
import java.util.Optional;

import com.baeldung.hexagonaldraft.domain.model.Product;


/**
 * The interface is an inbound port.
 */
public interface ProductService {

    List<Product> getProducts();

    Optional<Product> getProductById(Integer productId);

    Product addProduct(Product product);

    Optional<Product> removeProduct(Integer productId);
}
