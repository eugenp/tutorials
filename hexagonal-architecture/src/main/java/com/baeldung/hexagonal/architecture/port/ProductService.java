package com.baeldung.hexagonal.architecture.port;

import com.baeldung.hexagonal.architecture.domain.model.Product;
import java.util.List;

/**
 * The interface is an inbound port provides the flow and the application functionality to the outside
 */
public interface ProductService {

    List<Product> getProducts();

    Product getProductById(Integer productId);

    Product addProduct(Product product);

    Product removeProduct(Integer productId);
}
