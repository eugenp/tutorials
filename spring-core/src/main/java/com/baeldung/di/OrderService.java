package com.baeldung.di;

import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private UserService userService;
    private ProductService productService;

    public OrderService(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }
}