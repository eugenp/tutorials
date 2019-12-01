package com.baeldung.patterns.hexagonal.application;

import com.baeldung.patterns.hexagonal.domain.model.Product;

public class SimpleToStringOutputStrategy implements OutputStrategy {

    @Override
    public String formatAndOutput(Product product) {
        return product.toString();
    }
}
