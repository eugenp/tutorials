package com.baeldung.hexagonalarchitecture.ports;

import com.baeldung.hexagonalarchitecture.domain.Product;

import java.util.List;

public interface IProductProvider {

    List<Product> getProducts();
}
