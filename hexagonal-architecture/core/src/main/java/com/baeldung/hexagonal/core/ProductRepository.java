package com.baeldung.hexagonal.core;

import java.util.Optional;

public interface ProductRepository {

    Optional<Product> findProductById(Long productId);

}
