package com.baeldung.pattern.hexagonal.product.application.port.outbound;

import com.baeldung.pattern.hexagonal.product.domain.Product;

public interface LoadProductPort {
    Product getById(String productId);
}
