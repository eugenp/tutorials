package com.baeldung.apikeysecretauth.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.apikeysecretauth.model.Product;
import com.baeldung.apikeysecretauth.model.User;
import com.baeldung.apikeysecretauth.service.ProductService;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<Product> products(Authentication auth) {
        return productService.getAllProducts((User) auth.getPrincipal());
    }
}
