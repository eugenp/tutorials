package com.baeldung.hexagonal.architecture.adapter.rest;

import com.baeldung.hexagonal.architecture.domain.model.Product;
import com.baeldung.hexagonal.architecture.port.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The class defines a REST controller which is a primary adapter and invokes the inbound port of the application.
 */
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {
        return new ResponseEntity<>(productService.getProducts(), HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer productId) {
        return new ResponseEntity<>(productService.getProductById(productId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        return new ResponseEntity<>(productService.addProduct(product), HttpStatus.CREATED);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Product> removeProduct(@PathVariable Integer productId) {
        return new ResponseEntity<>(productService.removeProduct(productId), HttpStatus.OK);
    }
}
