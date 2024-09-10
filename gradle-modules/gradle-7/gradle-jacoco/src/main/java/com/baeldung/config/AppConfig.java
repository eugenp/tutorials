package com.baeldung.config;

import com.baeldung.service.ProductService;

public class AppConfig {

    public ProductService productService() {
        return new ProductService();
    }

}
