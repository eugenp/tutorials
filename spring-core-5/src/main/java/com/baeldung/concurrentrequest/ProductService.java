package com.baeldung.concurrentrequest;

import static java.util.Arrays.asList;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class ProductService {

    // @formatter:off
    private final static List<Product> productRepository = asList(
        new Product(1, "Product 1", new Stock(100)),
        new Product(2, "Product 2", new Stock(50))
    );
    // @formatter:on

    public Optional<Product> getProductById(int id) {
        return productRepository.stream()
            .filter(product -> product.getId() == id)
            .findFirst();
    }
}
