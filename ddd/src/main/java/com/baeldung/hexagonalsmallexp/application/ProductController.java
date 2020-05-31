package com.baeldung.hexagonalsmallexp.application;

import com.baeldung.hexagonalsmallexp.domain.Product;
import com.baeldung.hexagonalsmallexp.domain.service.ProductAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

@Component
public class ProductController {
    private Random random = new Random(1000);

    @Autowired
    private ProductAdapter productAdapter;

    public UUID createProduct() {
        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setPrice(random.nextInt(100));
        productAdapter.save(product);
        return product.getId();
    }

    public Product findProduct(UUID productId) {
        return productAdapter.findByProductId(productId);
    }
}
