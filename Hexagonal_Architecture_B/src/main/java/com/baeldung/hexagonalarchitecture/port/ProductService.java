package com.baeldung.hexagonalarchitecture.port;

import java.util.List;

import com.baeldung.hexagonalarchitecture.domain.model.Product;

/**
 * The interface is an inbound port provides the flow and the application functionality to the outside
 */
public interface ProductService {

    List<Product> getProducts();

    Product getProductById(Integer productId);

    Product addProduct(Product product);

    Product removeProduct(Integer productId);
}
