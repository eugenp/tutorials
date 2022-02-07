package com.baeldung.swaggerexample.controller;

import com.baeldung.swaggerexample.entity.Product;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ApiOperation("Products API")
public class ProductController {

    @ApiOperation(value = "Create a new product", notes = "Creates a new product as per the request body")
    @ApiResponses(value = {
      @ApiResponse(code = 201, message = "Successfully created"),
      @ApiResponse(code = 400, message = "Bad request - The product is not valid"),
      @ApiResponse(code = 500, message = "Internal server error - Something went wrong")
    })
    @PostMapping(value = "/products")
    public ResponseEntity<Void> createProduct(@RequestBody Product product) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get a product by id", notes = "Returns a product as per the id")
    @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successfully retrieved"),
      @ApiResponse(code = 404, message = "Not found - The product was not found")
    })
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") @ApiParam(name = "id", value = "Product id", example = "1") Long id) {
        //retrieval logic
        return ResponseEntity.ok(new Product(1, "Product 1", "$21.99"));
    }
}
