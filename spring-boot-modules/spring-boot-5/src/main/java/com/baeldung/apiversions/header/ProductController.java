package com.baeldung.apiversions.header;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.apiversions.model.ProductDto;
import com.baeldung.apiversions.model.ProductDtoV2;

@RestController
@RequestMapping(path = "/api/products")
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
    private final Map<String, ProductDto> productsMap =
        Map.of("1001", new ProductDto("1001", "apple", "apple_desc", 1.99));
    private final Map<String, ProductDtoV2> productsV2Map =
        Map.of("1001", new ProductDtoV2("1001", "apple", 1.99));

    @GetMapping(value = "/{id}", version = "1.0")
    public ProductDto getProductV1ById(@PathVariable String id) {
        LOGGER.info("Get Product version 1 for id {}", id);
        return productsMap.get(id);
    }

    @GetMapping(value = "/{id}", version = "2.0")
    public ProductDtoV2 getProductV2ById(@PathVariable String id) {
        LOGGER.info("Get Product version 2 for id {}", id);
        return productsV2Map.get(id);
    }
}
