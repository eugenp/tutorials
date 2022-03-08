package com.baeldung.swaggerresponseapi.controller;

import com.baeldung.swaggerresponseapi.model.Product;
import com.baeldung.swaggerresponseapi.service.ProductService;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    public Product addProduct(@RequestBody Product product) {
        return productService.addProducts(product);
    }

    @ApiResponses(value = { @ApiResponse(content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Product.class))) }) })
    @GetMapping("/products")
    public List<Product> getProductsList() {
        return productService.getProductsList();
    }
}
