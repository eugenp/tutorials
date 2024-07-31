package com.baeldung.jacocoexclusions.config;

import com.baeldung.jacocoexclusions.service.ProductService;

public class AppConfig {

    public ProductService productService() {
        return new ProductService();
    }

}
