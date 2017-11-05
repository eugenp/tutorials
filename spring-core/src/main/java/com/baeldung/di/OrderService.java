package com.baeldung.di;

@Service
public class OrderService {
    private final UserService userService;
    private final ProductService productService;

    public OrderService(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }
}