package com.baeldung.productservice.controller;

import com.baeldung.productservice.model.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    private final Map<Long, Product> productMap = new HashMap<>();

    @Operation(summary = "Get a product by its id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the product",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)) }),
        @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
        @ApiResponse(responseCode = "404", description = "Product not found", content = @Content) })
    @GetMapping(path = "/product/{id}")
    public Product getProduct(@Parameter(description = "id of product to be searched") @PathVariable("id") long productId){
        LOGGER.info("Getting Product Details for Product Id {}", productId);
        return productMap.get(productId);
    }

    @PostConstruct
    private void setupRepo() {
        Product product1 = getProduct(100001, "apple");
        productMap.put(100001L, product1);

        Product product2 = getProduct(100002, "pears");
        productMap.put(100002L, product2);

        Product product3 = getProduct(100003, "banana");
        productMap.put(100003L, product3);

        Product product4 = getProduct(100004, "mango");
        productMap.put(100004L, product4);
    }

    private static Product getProduct(int id, String name) {
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        return product;
    }
}
