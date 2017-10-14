package com.baeldung.dependency_injection_types.runners;

import com.baeldung.dependency_injection_types.entities.Product;
import com.baeldung.dependency_injection_types.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Order(value = 1)
public class ProductImporter implements CommandLineRunner {

    private ProductRepository productRepository;

    @Autowired
    public ProductImporter(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        productRepository.add(Product
          .builder()
          .name("product1")
          .code("code1")
          .price(BigDecimal.ONE)
          .build());
        System.out.println("imported a product");
    }
}
