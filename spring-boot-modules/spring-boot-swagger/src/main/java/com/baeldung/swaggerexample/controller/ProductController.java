package com.baeldung.swaggerexample.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.swaggerexample.entity.Product;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Products API")
public class ProductController {

    @Operation(summary = "Create a new product", description = "Creates a new product as per the request body")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"), @ApiResponse(responseCode = "400", description = "Bad request - The product is not valid"),
            @ApiResponse(responseCode = "500", description = "Internal server error - Something went wrong")
    })
    @PostMapping(value = "/products")
    public ResponseEntity<Void> createProduct(@RequestBody Product product) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Get a product by id", description = "Returns a product as per the id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"), @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") @Parameter(name = "id", description = "Product id", example = "1") Long id) {
        //retrieval logic
        return ResponseEntity.ok(new Product(1, "Product 1", "$21.99"));
    }
}
