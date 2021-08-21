package com.baeldung.hexagonal.domain.ports;

import com.baeldung.hexagonal.adapter.out.Product;

public interface ProductRepository {

    Product findByName(String name);
}
