package com.baeldung.patterns.hexagonal.application;

import com.baeldung.patterns.hexagonal.domain.model.Product;

public interface OutputStrategy {
    String formatAndOutput(Product product);
}
