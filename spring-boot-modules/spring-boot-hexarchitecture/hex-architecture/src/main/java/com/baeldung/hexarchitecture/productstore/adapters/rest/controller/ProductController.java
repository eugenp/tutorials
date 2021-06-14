package com.baeldung.hexarchitecture.productstore.adapters.rest.controller;

import com.baeldung.hexarchitecture.productstore.core.domain.model.Product;
import com.baeldung.hexarchitecture.productstore.ports.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {
        @Autowired
        private ProductService productService;

        @PostMapping
        public void addProduct(@RequestBody Product product) {
                productService.createProduct(product);
        }

        @GetMapping("/{id}")
        public Product getProduct(@PathVariable String id) {
                return productService.getProduct(id);
        }
}
