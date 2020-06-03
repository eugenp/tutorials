package com.baeldung.hexagonalsmallexp.application;

import com.baeldung.hexagonalsmallexp.domain.Product;
import com.baeldung.hexagonalsmallexp.domain.service.ProductPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.UUID;

@RestController
public class ProductController {
    private Random random = new Random(1000);

    @Autowired
    private ProductPort productPort;

    @PostMapping("/createProduct")
    public UUID createProduct() {
        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setPrice(random.nextInt(100));
        productPort.save(product);
        return product.getId();
    }

    @GetMapping("/product/{productId}")
    public Product findProduct(@PathVariable UUID productId) {
        return productPort.findByProductId(productId);
    }
}
