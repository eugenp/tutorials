package com.baeldung.apiversions.header;

import java.util.HashMap;
import java.util.Map;

import jakarta.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.apiversions.model.Product;
import com.baeldung.apiversions.model.ProductV2;

@RestController
@RequestMapping(path = "/api/products")
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
    private final Map<String, Product> productsMap = new HashMap<>();
    private final Map<String, ProductV2> productsV2Map = new HashMap<>();

    @GetMapping(value = "/{id}", version = "1.0")
    public Product getProductV1ById(@PathVariable String id) {
        LOGGER.info("Get Product version 1 for id {}", id);
        return productsMap.get(id);
    }

    @GetMapping(value = "/{id}", version = "2.0")
    public ProductV2 getProductV2ById(@PathVariable String id) {
        LOGGER.info("Get Product version 2 for id {}", id);
        return productsV2Map.get(id);
    }

    @PostConstruct
    public void init(){
        productsMap.put("1001", new Product("1001", "apple",
            "apple_desc", 1.99));
        productsV2Map.put("1001", new ProductV2("1001", "apple", 1.99));
    }
}
