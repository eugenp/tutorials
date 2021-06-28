package com.baeldung.pattern.architecture.hexagonal.controller;

import com.baeldung.pattern.architecture.hexagonal.model.Product;
import com.baeldung.pattern.architecture.hexagonal.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/product/{id}")
    Product getById(@PathVariable Long id) {
        return productService.getById(id);
    }

    @PostMapping("/product")
    void saveProduct(@RequestBody Product product) {
        productService.saveProduct(product);
    }

}