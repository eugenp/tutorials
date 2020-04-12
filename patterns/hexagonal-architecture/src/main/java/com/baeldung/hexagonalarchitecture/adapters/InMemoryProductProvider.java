package com.baeldung.hexagonalarchitecture.adapters;

import com.baeldung.hexagonalarchitecture.domain.Product;
import com.baeldung.hexagonalarchitecture.ports.IProductProvider;

import java.util.Arrays;
import java.util.List;

/**
 * Adapter is implementing the IProductProvider interface to manage Product I/O in memory
 */
public class InMemoryProductProvider implements IProductProvider {

    private static List<Product> PRODUCTS = Arrays.asList(
            new Product(1, "Shoes", 20.00),
            new Product(2, "Pants", 10.00),
            new Product(3, "Shirt", 5.00)
    );

    @Override
    public List<Product> getProducts() {
        return PRODUCTS;
    }

}
