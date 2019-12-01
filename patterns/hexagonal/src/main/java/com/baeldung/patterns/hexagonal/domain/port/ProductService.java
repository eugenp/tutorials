package com.baeldung.patterns.hexagonal.domain.port;

import com.baeldung.patterns.hexagonal.domain.exception.ProductNotFoundException;
import com.baeldung.patterns.hexagonal.domain.model.Product;
import java.util.List;

public interface ProductService {
    void addProducts(List<Product> products);
    Product getProduct(String id) throws ProductNotFoundException;
}
