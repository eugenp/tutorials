package com.baeldung.ecommerce.service;

import org.springframework.validation.annotation.Validated;

import com.baeldung.ecommerce.model.Product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Validated
public interface ProductService {

    @NotNull Iterable<Product> getAllProducts();

    Product getProduct(@Min(value = 1L, message = "Invalid product ID.") long id);

    Product save(Product product);
}
