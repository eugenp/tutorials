package com.baeldung.boot.hexagonal.web;

import com.baeldung.boot.hexagonal.core.domain.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ProductResource {

    @PostMapping
    void addProduct(@RequestBody Product product);


    @GetMapping("/{productId}")
    public Product getProduct(@PathVariable Long productId);

    @GetMapping
    public List<Product> allProducts();

}
