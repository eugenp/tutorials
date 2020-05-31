package com.baeldung.hexagonalsmallexp.infrastructure;

import com.baeldung.hexagonalsmallexp.domain.Product;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepostiory {

    void save(Product product);

    Product findById(UUID productId);
}
